package com.nexusex.ted.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by davinci42 on 2016/4/20.
 */
public class MusicInfoList implements Serializable {

	private static final long serialVersionUID = -6905306865533452780L;
	
	private String title;
	private List<MusicInfo> musicInfoList;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<MusicInfo> getMusicInfoList() {
		return musicInfoList;
	}

	public void setMusicInfoList(List<MusicInfo> musicInfoList) {
		this.musicInfoList = musicInfoList;
	}
}
