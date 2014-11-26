package lichen;

import java.util.ArrayList;
import java.util.Date;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

public class User {
	private String userName; // 用户的名字
	private String userPassword; // 用户的密码
	private ArrayList<Agenda> agendas;// 用户的所有议程

	public User() {
	}

	public User(String userName, String userPassword) {
		this.userName = userName;
		this.userPassword = userPassword;
		this.agendas = new ArrayList<Agenda>();
	}

	// 往用户的议程中添加议程
	public void addAgenda(int bookUserAgendaID, int bookedUserAgendaID,
			String descriptionLabel, String bookUserName,
			String bookedUserName, Date startTime, Date endTime) {
		
		Agenda temp = new Agenda(bookUserAgendaID, bookedUserAgendaID,
				descriptionLabel, bookUserName, bookedUserName, startTime,
				endTime);

		agendas.add(temp);
	}

	/**
	 * 以下是get函数们
	 */
	// 得到用户姓名
	public String getUserName() {
		return this.userName;
	}

	public String getUserPassword() {
		return this.userPassword;
	}

	public ArrayList<Agenda> getAgendas() {
		return this.agendas;
	}
	
}
