package lichen;

import java.util.Date;

public class Agenda {
	private int bookUserAgendaID; // 预约方的会晤的标识位
	private int bookedUserAgendaID; // 接受预约方会晤的标示位
	private String descriptionLabel;// 会晤标签
	private String bookUserName; // 发起会晤方的用户名
	private String bookUserPassword;// 发起会晤方的密码
	private String bookedUserName; // 接受会晤方的用户名
	private Date startTime; // 会晤的起始时间
	private Date endTime; // 会晤的终止时间

	// 无参构造函数
	public Agenda() {
	}

	// 有参构造函数1--用于构造实际的议程
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

	// 有参构造函数2--用于构造用于判断是否会冲突的temp议程
	public Agenda(Date startTime, Date endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}

	// 判断函数，用户判断该agenda是否会和agenda b冲突，如果发生冲突则返回true，否则返回false
	public boolean isConflictWith(Agenda b) {
		if (this.startTime.after(b.endTime)) {
			return false;
		}
		if (this.endTime.before(b.startTime)) {
			return false;
		}
		return true;
	}

	// 判断函数，用户判断该agenda是否包含着Agenda b,如果包含则返回true，否则返回false
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
	 * 以下是get函数
	 */
	
	//返回该会晤的会晤号
	public int getBookUserAgendaID(){
		return this.bookUserAgendaID;
	}
	
	//返回被预约方中该会晤的会晤号
	public int getBookedUserAgendaID(){
		return this.bookedUserAgendaID;
	}
	
	//返回被预约方的用户名
	public String getBookedUserName(){
		return this.bookedUserName;
	}
	
}
