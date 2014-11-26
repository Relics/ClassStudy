package lichen;

import java.util.Date;

public class Agenda {
	private int bookUserAgendaID; // ԤԼ���Ļ���ı�ʶλ
	private int bookedUserAgendaID; // ����ԤԼ������ı�ʾλ
	private String descriptionLabel;// �����ǩ
	private String bookUserName; // ���������û���
	private String bookUserPassword;// ������������
	private String bookedUserName; // ���ܻ�����û���
	private Date startTime; // �������ʼʱ��
	private Date endTime; // �������ֹʱ��

	// �޲ι��캯��
	public Agenda() {
	}

	// �вι��캯��1--���ڹ���ʵ�ʵ����
	public Agenda(int bookUserAgendaID, int bookedUserAgendaID,
			String descriptionLabel, String bookUserName,
			String bookedUserName, Date startTime, Date endTime) {
		this.bookUserAgendaID = bookUserAgendaID;
		this.bookedUserAgendaID = bookedUserAgendaID;
		this.descriptionLabel = descriptionLabel;
		this.bookUserName = bookUserName;
		this.bookedUserName = bookedUserName;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	// �вι��캯��2--���ڹ��������ж��Ƿ���ͻ��temp���
	public Agenda(Date startTime, Date endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}

	// �жϺ������û��жϸ�agenda�Ƿ���agenda b��ͻ�����������ͻ�򷵻�true�����򷵻�false
	public boolean isConflictWith(Agenda b) {
		if (this.startTime.after(b.endTime)) {
			return false;
		}
		if (this.endTime.before(b.startTime)) {
			return false;
		}
		return true;
	}

	// �жϺ������û��жϸ�agenda�Ƿ������Agenda b,��������򷵻�true�����򷵻�false
	public boolean isContain(Agenda b) {
		if ((b.startTime.after(this.startTime) || b.startTime
				.equals(this.startTime))
				&& (b.endTime.before(this.startTime) || b.endTime
						.equals(this.endTime))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ������get����
	 */
	
	//���ظû���Ļ����
	public int getBookUserAgendaID(){
		return this.bookUserAgendaID;
	}
	
	//���ر�ԤԼ���иû���Ļ����
	public int getBookedUserAgendaID(){
		return this.bookedUserAgendaID;
	}
	
	//���ر�ԤԼ�����û���
	public String getBookedUserName(){
		return this.bookedUserName;
	}
	
}
