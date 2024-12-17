package net.tfobz.vokabeltrainer.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;


/**
 * Stellt ein Fach dar, welches viele Karten enthalten kann. Das Fach enth�lt das Datum an welchem
 * zuletzt in ihm gelernt wurde. erinnerung legt fest, wie viele Tage vergehen m�ssen seit dem
 * letzten Lernen bis eine Erinnerung geworfen wird. Beispielsweise bedeutet eine 1, dass eine
 * Erinnerung geworfen wird, wenn zum letzten Mal gestern gelernt wurde. Ist erinnerung 0 so wird
 * nie eine Erinnerung geworfen. Wird dem Fach keine Beschreibung gegeben, so erh�lt das Fach
 * beim Hinzuf�gen zur Datenbank die Beschreibung "Fach <Nummer>" wobei <Nummer> die fortlaufende 
 * Nummer des Faches in der Lernkartei ist
 * @author Michael
 */
public class Fach 
{
  protected int nummer = -1;
  protected String beschreibung = null;
  protected int erinnerungsIntervall = 1;
  protected Date gelerntAm = null;

  protected Hashtable<String, String> fehler = null;
  
  public Fach() {
  }
  
  public Fach(int nummer, String beschreibung, int erinnerungsIntervall, Date gelerntAm) {
  	this.nummer = nummer;
  	this.setBeschreibung(beschreibung);
  	this.setErinnerungsIntervall(erinnerungsIntervall);
  	this.setGelerntAm(gelerntAm);
  }
  
  @Override
  public boolean equals(Object o) {
  	boolean ret = false;
  	if (o instanceof Fach) {
  		Fach f = (Fach)o;
  		if (f.nummer == nummer && 
  				(f.beschreibung == null && beschreibung == null || f.beschreibung.equals(beschreibung)) &&
  				f.erinnerungsIntervall == erinnerungsIntervall && 
  				(f.gelerntAm == null && gelerntAm == null || 
  				VokabeltrainerDB.convertToString(f.gelerntAm).equals(VokabeltrainerDB.convertToString(gelerntAm))))
  			ret = true;
  	}
  	return ret;
  }
  
	@Override
	public String toString() {
		String ret = null;
		ret = beschreibung + ", zuletzt gelernt am " + getGelerntAmEuropaeischString();
		if (getErinnerungFaellig())
			ret = ret + " ERINNERUNG";
		return ret;
	}
	
	/**
	 * Bei einem bereits existierenden Fach darf die Beschreibung nicht null sein
	 */
	public void validiere() {
		fehler = null;
		if (nummer != -1 && (beschreibung == null || beschreibung.length() == 0)) {
			fehler = new Hashtable<>();
			fehler.put("beschreibung", "Muss eingegeben werden");
		}
	}
	
	public Hashtable<String, String> getFehler() {
		return fehler;
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
	public int getErinnerungsIntervall() {
		return erinnerungsIntervall;
	}
	public void setErinnerungsIntervall(int erinnerungsIntervall) {
		if (erinnerungsIntervall >= 0)
			this.erinnerungsIntervall = erinnerungsIntervall;
	}
	public Date getGelerntAm() {
		return gelerntAm;
	}
	public String getGelerntAmString() {
		String ret = null;
		if (gelerntAm == null)
			ret = "";
		else
			ret = new SimpleDateFormat("yyyy-MM-dd").format(gelerntAm);
		return ret;
	}
	public String getGelerntAmEuropaeischString() {
		String ret = null;
		if (gelerntAm == null)
			ret = "NULL";
		else
			ret = new SimpleDateFormat("dd.MM.yyyy").format(gelerntAm);
		return ret;
	}
	public void setGelerntAm(Date gelerntAm) {
		this.gelerntAm = gelerntAm;
	}
	public boolean getErinnerungFaellig() {
		boolean ret = false;
		if (erinnerungsIntervall != 0) {
			Calendar calHeute = Calendar.getInstance();
			calHeute.setTime(new Date());
			calHeute.add(Calendar.DAY_OF_WEEK, -erinnerungsIntervall);
			calHeute.set(Calendar.HOUR, 0);
			calHeute.set(Calendar.MINUTE, 0);
			calHeute.set(Calendar.SECOND, 0);
			calHeute.set(Calendar.MILLISECOND, 0);
			Calendar calGelerntAm = Calendar.getInstance();
			calGelerntAm.setTime(gelerntAm);
			calGelerntAm.set(Calendar.HOUR, 0);
			calGelerntAm.set(Calendar.MINUTE, 0);
			calGelerntAm.set(Calendar.SECOND, 0);
			calGelerntAm.set(Calendar.MILLISECOND, 0);
			ret = calHeute.getTime().getTime() - calGelerntAm.getTime().getTime() >= 0;
		}
		return ret;
	}
}