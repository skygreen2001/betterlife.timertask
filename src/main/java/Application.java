import java.util.Set;

import org.reflections.Reflections;
import org.springframework.boot.SpringApplication;

import common.Gc;

import tasks.Tasks;

/**
 * Java定时任务
 * 参考:http://spring.io/guides/gs/scheduling-tasks/
 * @author skygreen2001@gmail.com
 *
 */
public class Application {
	public static void main(String[] args) throws Exception {
		if (Gc.isRunTasks)runTasks();
	}
	
	/**
	 * 运行所有定义在Package:tasks.run下的任务
	 */
	public static void runTasks()
	{
		Reflections reflections = new Reflections("tasks.run");

		Set<Class<? extends Tasks>> allClasses = reflections.getSubTypesOf(Tasks.class);
		for (Class<? extends Tasks> object : allClasses) {
			SpringApplication.run(object);
		}
//		SpringApplication.run(DaoTasks.class);
//		SpringApplication.run(DbInfoTasks.class);
//		SpringApplication.run(ReportTimesTasks.class);
//		SpringApplication.run(SendMailTasks.class);
		
	}
}
