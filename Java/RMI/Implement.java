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
	private HashMap<String, User> userInfos; // ���ڴ���û����û�����������Ϣ

	// �޲ι��캯��
	public Implement() throws RemoteException {
		userInfos = new HashMap<String, User>();

	}

	// ע�ắ��������û��ɹ�ע�ᣬ����true�����򷵻�false
	public boolean register(String userName, String userPassword)
			throws RemoteException {
		if (isRegister(userName)) {
			return false;
		} else {
			userInfos.put(userName, new User(userName, userPassword));// ����û���Ϣ
		}
		return true;
	}

	// ��ӻ���,�������»���ɹ����򷵻�true�����򷵻�false
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
					// ��ԤԼ����ӻ���
					bookTemp.addAgenda(bookTemp.getAgendas().size(), bookedTemp
							.getAgendas().size(), descriptionLabel,
							bookUserName, bookedUserName, startTime, endTime);
					// �ڱ�ԤԼ����ӻ���
					bookedTemp.addAgenda(bookedTemp.getAgendas().size(),
							bookTemp.getAgendas().size(), descriptionLabel,
							bookUserName, bookedUserName, startTime, endTime);

				} else {
					System.out.println("������̻�Է��������û����ͻ");
					return false;
				}
			} else {
				System.out.println("�����û��������벻��ȷ��ԤԼ��������");
				return false;
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
		return true;
	}

	// ��ѯ�������һ����ָ��ʱ����ڵ����л����б�
	public ArrayList<Agenda> queryAgendas(String userName, String userPassword,
			int startMonth, int startDay, int startYear, int endMonth,
			int endDay, int endYear) throws RemoteException {
		// ������������л����б�
		ArrayList<Agenda> ans = new ArrayList<Agenda>();
		// ����Ŀ�ʼʱ��
		String strStart = new String("" + startMonth + "-" + startDay + "-"
				+ startYear);
		// ����Ľ���ʱ��
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
				System.out.println("�����û��������벻��ȷ");
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return ans;
	}

	// ɾ��ĳ��������ɾ���ɹ��򷵻�true�����򷵻�false
	public boolean deleteOneAgenda(String userName, String userPassword,
			int deleteAgendaID) throws RemoteException {

		if ((isUserValidate(userName, userPassword))
				&& (userInfos.containsKey(userName))) {

			User bookTemp = userInfos.get(userName);

			for (Agenda i : bookTemp.getAgendas()) {
				if (deleteAgendaID == i.getBookUserAgendaID()) {
					// ��ԤԼ���û�
					User bookedTemp = userInfos.get(i.getBookedUserName());
					// ��ԤԼ���û�Ҫɾ���Ļ������
					int deleteBookedAgendaID = i.getBookedUserAgendaID();
					for (Agenda j : bookedTemp.getAgendas()) {
						if (deleteBookedAgendaID == j.getBookedUserAgendaID()) {
							// ɾ��ԤԼ��
							bookTemp.getAgendas().remove(i);
							// ɾ����ԤԼ��
							bookedTemp.getAgendas().remove(j);
							return true;// ֻ�е�ԤԼ���ͱ�ԤԼ��������ɾ������Ч
						}
					}
				}
			}
		} else {
			System.out.println("�����û��������벻��ȷ");
			return false;
		}
		return false;
	}

	// ɾ��ĳ���û������л�����ɾ���ɹ��򷵻�true�����򷵻�false
	public boolean clearAllTheAgendas(String userName, String userPassword)
			throws RemoteException {

		if ((isUserValidate(userName, userPassword))
				&& (userInfos.containsKey(userName))) {

			User bookTemp = userInfos.get(userName);

			for (Agenda i : bookTemp.getAgendas()) {
				// ��ԤԼ���û�
				User bookedTemp = userInfos.get(i.getBookedUserName());
				// ��ԤԼ���û�Ҫɾ���Ļ������
				int deleteBookedAgendaID = i.getBookedUserAgendaID();
				for (Agenda j : bookedTemp.getAgendas()) {
					if (deleteBookedAgendaID == j.getBookedUserAgendaID()) {
						// ɾ��ԤԼ��
						bookTemp.getAgendas().remove(i);
						// ɾ����ԤԼ��
						bookedTemp.getAgendas().remove(j);
					}
				}
			}
		} else {
			System.out.println("�����û��������벻��ȷ");
			return false;
		}
		return true;
	}

	// �ڲ��жϺ�����������û����Ѿ�ע�ᣬ�򷵻�true�����򷵻�false
	private boolean isRegister(String userName) {
		// ���Ϊ��ֱ��ע�ἴ��
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

	// �ڲ��жϺ���������û��û��������붼��ȷ���򷵻�true����������û�������������򷵻�false
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

	// �ڲ��жϺ������жϸ�����Ƿ���û�a���û�b�е�����һ���Ѿ�������̳�ͻ
	private boolean isWeCouldMeet(User bookTemp, User bookedTemp,
			Agenda agendaTemp) {
		for (Agenda i : bookTemp.getAgendas()) {
			if (i.isConflictWith(agendaTemp))
				return false;// �г�ͻ
		}
		for (Agenda i : bookedTemp.getAgendas()) {
			if (i.isConflictWith(agendaTemp))
				return false;// �г�ͻ
		}
		return true;// ���Թ���
	}
}