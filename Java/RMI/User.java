package lichen;

import java.util.ArrayList;
import java.util.Date;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

public class User {
	private String userName; // �û�������
	private String userPassword; // �û�������
	private ArrayList<Agenda> agendas;// �û����������

	public User() {
	}

	public User(String userName, String userPassword) {
		this.userName = userName;
		this.userPassword = userPassword;
		this.agendas = new ArrayList<Agenda>();
	}

	// ���û��������������
	public void addAgenda(int bookUserAgendaID, int bookedUserAgendaID,
			String descriptionLabel, String bookUserName,
			String bookedUserName, Date startTime, Date endTime) {
		
		Agenda temp = new Agenda(bookUserAgendaID, bookedUserAgendaID,
				descriptionLabel, bookUserName, bookedUserName, startTime,
				endTime);

		agendas.add(temp);
	}

	/**
	 * ������get������
	 */
	// �õ��û�����
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
