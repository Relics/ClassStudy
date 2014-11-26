package lichen;

import java.awt.List;
import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.HashMap;

import lichen.User;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author Lichen
 */

public class Implement extends UnicastRemoteObject implements Interface {
	private HashMap<String, User> userInfos; // 用于存放用户的用户名和密码信息

	// 无参构造函数
	public Implement() throws RemoteException {
		userInfos = new HashMap<String, User>();

	}

	// 注册函数，如果用户成功注册，返回true，否则返回false
	public boolean register(String userName, String userPassword)
			throws RemoteException {
		if (isRegister(userName)) {
			return false;
		} else {
			userInfos.put(userName, new User(userName, userPassword));// 添加用户信息
		}
		return true;
	}

	// 添加会晤,如果添加新会晤成功，则返回true，否则返回false
	public boolean addAgenda(String bookUserName, String bookUserPassword,
			String bookedUserName, String descriptionLabel, int startMonth,
			int startDay, int startYear, int endMonth, int endDay, int endYear)
			throws RemoteException {

		String strStart = new String("" + startMonth + "-" + startDay + "-"
				+ startYear);
		String strEnd = new String("" + endMonth + "-" + endDay + "-" + endYear);

		try {
			SimpleDateFormat bartDateFormat = new SimpleDateFormat("MM-dd-yyyy");
			Date startTime = bartDateFormat.parse(strStart);
			Date endTime = bartDateFormat.parse(strEnd);
			if ((isUserValidate(bookedUserName, bookUserPassword))
					&& (userInfos.containsKey(bookedUserName))) {

				User bookTemp = userInfos.get(bookUserName);
				User bookedTemp = userInfos.get(bookedUserName);
				Agenda agendaTemp = new Agenda(startTime, endTime);

				if (isWeCouldMeet(bookTemp, bookedTemp, agendaTemp)) {
					// 在预约方添加会晤
					bookTemp.addAgenda(bookTemp.getAgendas().size(), bookedTemp
							.getAgendas().size(), descriptionLabel,
							bookUserName, bookedUserName, startTime, endTime);
					// 在被预约方添加会晤
					bookedTemp.addAgenda(bookedTemp.getAgendas().size(),
							bookTemp.getAgendas().size(), descriptionLabel,
							bookUserName, bookedUserName, startTime, endTime);

				} else {
					System.out.println("您的议程或对方的议程与该会晤冲突");
					return false;
				}
			} else {
				System.out.println("您的用户名和密码不正确或预约方不存在");
				return false;
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
		return true;
	}

	// 查询会晤，返回一个在指定时间段内的所有会晤列表
	public ArrayList<Agenda> queryAgendas(String userName, String userPassword,
			int startMonth, int startDay, int startYear, int endMonth,
			int endDay, int endYear) throws RemoteException {
		// 最终输出的所有会晤列表
		ArrayList<Agenda> ans = new ArrayList<Agenda>();
		// 会晤的开始时间
		String strStart = new String("" + startMonth + "-" + startDay + "-"
				+ startYear);
		// 会晤的结束时间
		String strEnd = new String("" + endMonth + "-" + endDay + "-" + endYear);
		try {
			SimpleDateFormat bartDateFormat = new SimpleDateFormat("MM-dd-yyyy");
			Date startTime = bartDateFormat.parse(strStart);
			Date endTime = bartDateFormat.parse(strEnd);

			if ((isUserValidate(userName, userPassword))
					&& (userInfos.containsKey(userName))) {

				User bookTemp = userInfos.get(userName);
				Agenda agendaTemp = new Agenda(startTime, endTime);

				for (Agenda i : bookTemp.getAgendas()) {
					if (agendaTemp.isContain(i)) {
						ans.add(i);
					}
				}
			} else {
				System.out.println("您的用户名和密码不正确");
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return ans;
	}

	// 删除某个会晤，如果删除成功则返回true，否则返回false
	public boolean deleteOneAgenda(String userName, String userPassword,
			int deleteAgendaID) throws RemoteException {

		if ((isUserValidate(userName, userPassword))
				&& (userInfos.containsKey(userName))) {

			User bookTemp = userInfos.get(userName);

			for (Agenda i : bookTemp.getAgendas()) {
				if (deleteAgendaID == i.getBookUserAgendaID()) {
					// 被预约的用户
					User bookedTemp = userInfos.get(i.getBookedUserName());
					// 被预约的用户要删除的会晤号码
					int deleteBookedAgendaID = i.getBookedUserAgendaID();
					for (Agenda j : bookedTemp.getAgendas()) {
						if (deleteBookedAgendaID == j.getBookedUserAgendaID()) {
							// 删除预约方
							bookTemp.getAgendas().remove(i);
							// 删除被预约方
							bookedTemp.getAgendas().remove(j);
							return true;// 只有当预约方和被预约方都存在删除才有效
						}
					}
				}
			}
		} else {
			System.out.println("您的用户名和密码不正确");
			return false;
		}
		return false;
	}

	// 删除某个用户的所有会晤，如果删除成功则返回true，否则返回false
	public boolean clearAllTheAgendas(String userName, String userPassword)
			throws RemoteException {

		if ((isUserValidate(userName, userPassword))
				&& (userInfos.containsKey(userName))) {

			User bookTemp = userInfos.get(userName);

			for (Agenda i : bookTemp.getAgendas()) {
				// 被预约的用户
				User bookedTemp = userInfos.get(i.getBookedUserName());
				// 被预约的用户要删除的会晤号码
				int deleteBookedAgendaID = i.getBookedUserAgendaID();
				for (Agenda j : bookedTemp.getAgendas()) {
					if (deleteBookedAgendaID == j.getBookedUserAgendaID()) {
						// 删除预约方
						bookTemp.getAgendas().remove(i);
						// 删除被预约方
						bookedTemp.getAgendas().remove(j);
					}
				}
			}
		} else {
			System.out.println("您的用户名和密码不正确");
			return false;
		}
		return true;
	}

	// 内部判断函数，如果该用户名已经注册，则返回true，否则返回false
	private boolean isRegister(String userName) {
		// 如果为空直接注册即可
		if (userInfos.isEmpty())
			return true;
		else {
			if (userInfos.containsKey(userName)) {
				return true;
			} else {
				return false;
			}
		}
	}

	// 内部判断函数，如果用户用户名和密码都正确，则返回true，否则如果用户名或密码错误，则返回false
	private boolean isUserValidate(String userName, String userPassword) {
		if (userInfos.containsKey(userName)) {
			if (userInfos.get(userName).getUserPassword().equals(userPassword)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	// 内部判断函数，判断该议程是否和用户a，用户b中的任意一个已经存在议程冲突
	private boolean isWeCouldMeet(User bookTemp, User bookedTemp,
			Agenda agendaTemp) {
		for (Agenda i : bookTemp.getAgendas()) {
			if (i.isConflictWith(agendaTemp))
				return false;// 有冲突
		}
		for (Agenda i : bookedTemp.getAgendas()) {
			if (i.isConflictWith(agendaTemp))
				return false;// 有冲突
		}
		return true;// 可以共存
	}
}