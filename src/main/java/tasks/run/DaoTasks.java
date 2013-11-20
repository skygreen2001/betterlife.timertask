package tasks.run;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import tasks.Tasks;
import common.Gc;
import common.log.LogMe;
import dao.Sqlscript;
import domain.mysql.Admin;
import domain.sqlserver.SAdmin;

/**
 * 测试显示Mysql/Sqlserver数据CRUD功能
 * @author skygreen2001@gmail.com
 */
@EnableScheduling
public class DaoTasks extends Tasks {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"HH:mm:ss");

	@Scheduled(cron = "*/5 * * * * SUN-SAT")
	public void runMysqlTest() {
		if (Gc.isCloseMysql)return;
		LogMe.debug("执行Mysql Test开始时间: " + dateFormat.format(new Date()));
		Admin admin = new Admin();
		admin.setUsername("aaaa");
		admin.setPassword("1111");
		admin.setLoginTimes(0);
		admin.saveOrUpdate();
		Long id = (Long) admin.save();
		admin = Admin.get_by_id(id);
		admin.setUsername("bbbb");
		admin.setPassword("2222");
		admin.saveOrUpdate();
		admin.setUsername("ccc");
		admin.update();
		Admin.dao().deleteByID(id);
		Admin.dao().deleteByIds("56,58");
		Admin.dao().deleteBy(Admin.id_name + "=51");
		Admin.dao().updateProperties("54,55", "realname='ddd',username='eee'");
		Admin.dao().updateBy(Admin.id_name + "=57 or " + Admin.id_name + "=58",
				"realname='ffff',username='gggg'");
		Admin.dao().increment(Admin.id_name + "=59", "loginTimes", 1);
		Admin.dao().increment(Admin.id_name + "=59", "loginTimes", 1);
		Admin.dao().decrement(Admin.id_name + "=59", "loginTimes", 1);
		Boolean isExist = Admin.dao().existByID(16);
		if (isExist)
			LogMe.debug("标识的对象存在 ");
		isExist = Admin.dao().existBy("username='aaaa'");
		if (isExist)
			LogMe.debug("标识的对象存在 ");
		admin = Admin.get_by_id(1l);
		if (admin != null) {
			LogMe.debug("姓    名： " + admin.getUsername());
			LogMe.debug("密码： " + admin.getPassword());
		} else {
			LogMe.debug("未找到！");
		}

		long count = (Long) Admin.dao().count("username='aaaa'");
		LogMe.debug("总数量： " + count);
		count = (Long) Admin.dao().count();

		Map<Serializable, Admin> lists;
		if (count > 0) {
			int pagesize = 10;
			int countAllPage = (int) (count / pagesize + 1);
			for (int i = 0; i < countAllPage; i++) {
				lists = Admin.queryPage(i * pagesize + 1, (i + 1) * pagesize);
				for (Admin admin_ele : lists.values()) {
					LogMe.debug("标  识： " + admin_ele.getAdmin_id()
							+ ";姓    名： " + admin_ele.getUsername() + ";密码： "
							+ admin_ele.getPassword());
				}
			}
			LogMe.debug("执行方式与众不同: ");
			for (int i = 0; i < countAllPage; i++) {
				lists = Admin.queryPageByPageNo(i + 1, pagesize);
				for (Admin admin_ele : lists.values()) {
					LogMe.debug("标  识： " + admin_ele.getAdmin_id()
							+ ";姓    名： " + admin_ele.getUsername() + ";密码： "
							+ admin_ele.getPassword());
				}
			}
		}
		lists = Admin.get();
		LogMe.debug("执行get方法: ");
		for (Admin admin_ele : lists.values()) {
			LogMe.debug("标  识： " + admin_ele.getAdmin_id() + ";姓    名： "
					+ admin_ele.getUsername() + ";密码： "
					+ admin_ele.getPassword());
		}
		List<Object> list = Admin.dao().select("username,realname",
				Admin.id_name + ">10", null, null);
		LogMe.debug("执行select方法: ");
		for (int i = 0; i < list.size(); i++) {
			Object[] data = (Object[]) list.get(i);
			LogMe.debug(data[0] + "," + data[1]);

		}
		String one = (String) Admin.dao().select_one("username",
				Admin.id_name + ">10", null);
		LogMe.debug("执行select_one方法: " + one);

		long max = (Long) Admin.dao().max();
		long min = (Long) Admin.dao().min();
		long sum = (Long) Admin.dao().sum();
		LogMe.debug("最大值： " + max + ";最小值： " + min + ";总和： " + sum);

		List<Map<String, Object>> listAll = Sqlscript.mysql.query(Gc
				.sqlscript().get("takeBlog"));
		for (Map<String, Object> map : listAll) {
			LogMe.debug(map.get("username") + "," + map.get("blog_name"));
		}
		LogMe.debug("执行Mysql结束时间: " + dateFormat.format(new Date()));
	}

	@Scheduled(cron = "*/6 * * * * SUN-SAT")
	public void runSqlserverTest() {
		if (Gc.isCloseSqlserver)return;
		LogMe.debug("执行Sqlserver Test开始时间: "
				+ dateFormat.format(new Date()));

		SAdmin admins = new SAdmin();
		admins.setUsername("aaa");
		admins.setPassword("111");
		admins.saveOrUpdate();
		String uuid = (String) admins.save();

		admins = SAdmin.get_by_id(uuid);
		admins.setUsername("bbbb");
		admins.setPassword("2222");
		admins.saveOrUpdate();

		admins.setUsername("ccc");
		admins.update();

		SAdmin.dao()
				.updateProperties(
						"{797D77F9-B38E-4C9C-AAFE-331321398878},{37744BAB-FCD1-456B-9D04-8B617B096593}",
						"Realname='ddd',Username='eee'");
		SAdmin.dao().updateBy(
				SAdmin.id_name
						+ "='{797D77F9-B38E-4C9C-AAFE-331321398878}' or "
						+ SAdmin.id_name
						+ "='{37744BAB-FCD1-456B-9D04-8B617B096593}'",
				"Realname='ffff',Username='gggg'");
		SAdmin.dao().increment(SAdmin.id_name + "='{" + uuid + "}'",
				"LoginTimes", 1);
		SAdmin.dao().increment(SAdmin.id_name + "='{" + uuid + "}'",
				"LoginTimes", 1);
		SAdmin.dao().decrement(SAdmin.id_name + "='{" + uuid + "}'",
				"LoginTimes", 1);

		SAdmin.dao().deleteByID("{797D77F9-B38E-4C9C-AAFE-331321398878}");
		SAdmin.dao()
				.deleteByIds(
						"{797D77F9-B38E-4C9C-AAFE-331321398878},{37744BAB-FCD1-456B-9D04-8B617B096593}");
		SAdmin.dao().deleteBy(
				SAdmin.id_name + "='{D2623B6D-709F-45DF-9663-565B1C56B10E}'");

		Boolean isExists = SAdmin.dao().existByID(
				"{797D77F9-B38E-4C9C-AAFE-331321398878}");
		if (isExists)
			LogMe.debug("标识的对象存在 ");
		isExists = SAdmin.dao().existBy("Username='aaa'");
		if (isExists)
			LogMe.debug("标识的对象存在 ");
		admins = SAdmin.get_by_id("{82AD4607-F9E0-4381-8982-74EECACA4837}");
		if (admins != null) {
			LogMe.debug("姓    名： " + admins.getUsername());
			LogMe.debug("密码： " + admins.getPassword());
		} else {
			LogMe.debug("未找到！");
		}

		long counts = (Long) SAdmin.dao().count("username='aaa'");
		LogMe.debug("总数量： " + counts);
		counts = (Long) SAdmin.dao().count();
		Map<Serializable, SAdmin> listss;
		if (counts > 0) {
			int pagesize = 10;

			int countAllPage = (int) (counts / pagesize + 1);
			for (int i = 0; i < countAllPage; i++) {
				listss = SAdmin.queryPage(i * pagesize + 1, (i + 1) * pagesize);
				for (SAdmin admin_ele : listss.values()) {
					LogMe.debug("标  识： " + admin_ele.getID()
							+ ";姓    名： " + admin_ele.getUsername() + ";密码： "
							+ admin_ele.getPassword());
				}
			}
			LogMe.debug("执行方式与众不同: ");
			for (int i = 0; i < countAllPage; i++) {
				listss = SAdmin.queryPageByPageNo(i + 1, pagesize);
				for (SAdmin admin_ele : listss.values()) {
					LogMe.debug("标  识： " + admin_ele.getID()
							+ ";姓    名： " + admin_ele.getUsername() + ";密码： "
							+ admin_ele.getPassword());
				}
			}
		}
		listss = SAdmin.get();
		LogMe.debug("执行get方法: ");
		for (SAdmin admin_ele : listss.values()) {
			LogMe.debug("标  识： " + admin_ele.getID() + ";姓    名： "
					+ admin_ele.getUsername() + ";密码： "
					+ admin_ele.getPassword());
		}
		List<Object> list_s = SAdmin.dao().select("id,username,realname",
				"realname is null", null, null);
		LogMe.debug("执行select方法: ");
		for (int i = 0; i < list_s.size(); i++) {
			Object[] data = (Object[]) list_s.get(i);
			LogMe.debug(data[0] + "," + data[1]);

		}
		String ones = (String) SAdmin.dao().select_one("username",
				"realname is null", null);
		LogMe.debug("执行select_one方法: " + ones);

		Integer maxs = (Integer) SAdmin.dao().max("loginTimes");
		Integer mins = (Integer) SAdmin.dao().min("loginTimes");
		Long sums = (Long) SAdmin.dao().sum("loginTimes");
		LogMe.debug("最大值： " + maxs + ";最小值： " + mins + ";总和： " + sums);

		List<Map<String, Object>> listAlls = Sqlscript.sqlserver.query(Gc
				.sqlscript_sqlserver().get("takeBlog"));
		for (Map<String, Object> map : listAlls) {
			LogMe.debug(map.get("Username") + "," + map.get("Blog_Name"));
		}
		LogMe.debug("执行Sqlserver结束时间: " + dateFormat.format(new Date()));

	}
}
