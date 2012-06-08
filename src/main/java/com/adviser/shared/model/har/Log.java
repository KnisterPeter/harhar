package com.adviser.shared.model.har;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class Log implements Serializable {
	private String version;
	private Creator creator;
	private List<Page> pages = new LinkedList<Page>();
	private List<Entry> entries = new LinkedList<Entry>();
	
	public List<Entry> getEntriesByPage(Page page) {
		final List<Entry> ret = new LinkedList<Entry>();
		for(Entry e: entries) {
			if (e.getPageref().equals(page.getId())) {
				ret.add(e);
			}
		}
		return ret;
	}
	
	public void addLog(Log log) {
		if (version == null) {
			version = log.version;
		}
		if (creator == null) {
			creator = log.creator;
		}
		// Merge Pages
		Map<String, Page> logPages = new HashMap<String, Page>();
		for(Page p: log.pages) {
			logPages.put(p.getId(), p);
		}
		for(Page p: pages) {
			Page logPage = logPages.get(p.getId());
			if (logPage != null) {
				p = logPage;
				logPages.remove(p.getId());
			}
		}
		for(Page p: logPages.values()) {
			pages.add(p);
		}
		// Merge Entries		
		Map<String, Map<String, Entry>> logEntries = new HashMap<String, Map<String, Entry>>();
		for(Entry e: log.entries) {
			Map<String, Entry> entries = logEntries.get(e.getPageref());
			if (entries == null) {
				entries = new HashMap<String, Entry>();
				logEntries.put(e.getPageref(), entries);
			}
			entries.put(e.getRequest().getUrl(), e);
		}
		for(Entry e: entries) {
			Map<String, Entry> map = logEntries.get(e.getPageref());
			if (map != null) {
				entries.remove(e);
			}
		}
		for(Map<String, Entry> es: logEntries.values()) {
			for(Entry e: es.values()) {
				entries.add(e);
			}
		}
		
		
	}
}
