package xyz.fandazky.demomybatis;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
//@EnableScheduling
public class DemoMybatisApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoMybatisApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<Employee> employees = new ArrayList<>();
		for (int i=0; i<100000; i++) {
			employees.add(new Employee(i, "name_"+i));
		}

		long beforeStream = System.currentTimeMillis();
		List<Employee> streamEmployees = loopWithStream(employees);
		long timeStream = System.currentTimeMillis() - beforeStream;
		System.out.println("total employee:: " + streamEmployees.size());
		System.out.println("Time stream:: "+ String.valueOf(timeStream));

		long beforeIterator = System.currentTimeMillis();
		List<Employee> iteratorEmployee = loopByIterator(employees);
		long timeIterator = System.currentTimeMillis() - beforeIterator;
		System.out.println("total employee:: " + iteratorEmployee.size());
		System.out.println("Time iterator:: "+ String.valueOf(timeIterator));

	}

	public List<Employee> loopWithStream(List<Employee> employees) {
		return employees.stream()
				.filter(employee -> employee.getName().contains("name_1"))
				.collect(Collectors.toList());
	}

	public List<Employee> loopByIterator(List<Employee> employees) {
		Iterator<Employee> iterator = employees.iterator();
		List<Employee> filteredEmployee = new ArrayList<>();
		while (iterator.hasNext()) {
			Employee employee = iterator.next();
			if(employee.getName().contains("name_1")) {
				filteredEmployee.add(employee);
			}
		}
		return filteredEmployee;
	}

}
