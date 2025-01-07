package net.tfobz.vokabeltrainer.model;

import java.util.Hashtable;

/**
 * Bei einer Lernkartei m�ssen die Beschreibung sowie die Beschreibung der beiden W�rter eingegeben werden 
 * @author Michael
 */
public class Lernkartei
{
	protected int nummer = -1;
	protected String beschreibung = null;
	protected String wortEinsBeschreibung = null;
	protected String wortZweiBeschreibung = null;
	protected boolean richtung = true;
	protected boolean grossKleinschreibung = false;

	protected Hashtable<String, String> fehler = null;

	public Lernkartei() {
	}

	public Lernkartei(int nummer, String beschreibung, String wortEinsBeschreibung, String wortZweiBeschreibung,
					  boolean richtung, boolean grossKleinschreibung) {
		this.nummer = nummer;
		this.beschreibung = beschreibung;
		this.wortEinsBeschreibung = wortEinsBeschreibung;
		this.wortZweiBeschreibung = wortZweiBeschreibung;
		this.richtung = richtung;
		this.grossKleinschreibung = grossKleinschreibung;
	}

	public Lernkartei(String beschreibung, String wortEinsBeschreibung, String wortZweiBeschreibung,
					  boolean richtung, boolean grossKleinschreibung) {
		this.beschreibung = beschreibung;
		this.wortEinsBeschreibung = wortEinsBeschreibung;
		this.wortZweiBeschreibung = wortZweiBeschreibung;
		this.richtung = richtung;
		this.grossKleinschreibung = grossKleinschreibung;
	}

	@Override
	public boolean equals(Object o) {
		boolean ret = false;
		if (o != null && o instanceof Lernkartei) {
			Lernkartei k = (Lernkartei)o;
			if (k.nummer == nummer &&
					(k.wortEinsBeschreibung == null && wortEinsBeschreibung == null ||
							k.wortEinsBeschreibung.equals(wortEinsBeschreibung)) &&
					(k.wortZweiBeschreibung == null && wortZweiBeschreibung == null ||
							k.wortZweiBeschreibung.equals(wortZweiBeschreibung)) &&
					k.richtung == richtung &&
					k.grossKleinschreibung == grossKleinschreibung)
				ret = true;
		}
		return ret;
	}

	@Override
	public String toString() {
		return beschreibung;
	}

	/**
	 * Kontrolliert ob wortEinsBeschreibung und wortZweiBeschreibung eingegeben wurden
	 */
	public void validiere() {
		fehler = null;
		if (beschreibung == null || beschreibung.length() == 0) {
			fehler = new Hashtable<String, String>();
			fehler.put("beschreibung", "Muss eingegeben werden");
		}
		if (wortEinsBeschreibung == null || wortEinsBeschreibung.length() == 0) {
			if (fehler == null)
				fehler = new Hashtable<String, String>();
			fehler.put("wortEinsBeschreibung", "Muss eingegeben werden");
		}
		if (wortZweiBeschreibung == null || wortZweiBeschreibung.length() == 0) {
			if (fehler == null)
				fehler = new Hashtable<String, String>();
			fehler.put("wortZweiBeschreibung", "Muss eingegeben werden");
		}
	}

	public int getNummer() {
		return nummer;
	}
	public String getBeschreibung() {
		return beschreibung;
	}
	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
		if (this.beschreibung != null)
			this.beschreibung = this.beschreibung.trim();
	}
	public String getWortEinsBeschreibung() {
		return wortEinsBeschreibung;
	}
	public void setWortEinsBeschreibung(String wortEinsBeschreibung) {
		this.wortEinsBeschreibung = wortEinsBeschreibung;
		if (this.wortEinsBeschreibung != null)
			this.wortEinsBeschreibung = this.wortEinsBeschreibung.trim();
	}
	public String getWortZweiBeschreibung() {
		return wortZweiBeschreibung;
	}
	public void setWortZweiBeschreibung(String wortZweiBeschreibung) {
		this.wortZweiBeschreibung = wortZweiBeschreibung;
		if (this.wortZweiBeschreibung != null)
			this.wortZweiBeschreibung = this.wortZweiBeschreibung.trim();
	}
	public boolean getRichtung() {
		return richtung;
	}
	public void setRichtung(boolean richtung) {
		this.richtung = richtung;
	}
	public boolean getGrossKleinschreibung() {
		return grossKleinschreibung;
	}
	public void setGrossKleinschreibung(boolean grossKleinschreibung) {
		this.grossKleinschreibung = grossKleinschreibung;
	}
	public Hashtable<String, String> getFehler() {
		return fehler;
	}
}