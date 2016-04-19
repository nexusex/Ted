package com.nexusex.ted.bean;

import android.support.annotation.NonNull;
import com.nexusex.ted.utils.Utils;
import java.io.Serializable;

/**
 * Created by davinci42 on 2016/4/13.
 */
public class MusicInfo implements Serializable, Comparable<MusicInfo> {

	private static final long serialVersionUID = 280394661099113257L;

	private String title;
	private long id;
	private long duration;
	private String artist;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getFormattedDuration() {
		return Utils.convertSecondsToMmSs(getDuration());
	}

	@Override public int compareTo(@NonNull MusicInfo another) {
		return getTitle().compareToIgnoreCase(another.getTitle());
	}
}
