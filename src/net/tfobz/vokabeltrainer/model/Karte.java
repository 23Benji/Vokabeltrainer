package net.tfobz.vokabeltrainer.model;

import java.util.Hashtable;

/**
 * Stellt eine Karte dar, welche zwei W�rter, eine Abfragerichtung und die Information ob beim Vergleichen
 * der W�rter die Gro�-/Kleinschreibung ber�cksichtigt werden soll, enth�lt. Bei einer Karte m�ssen
 * die beiden W�rter eingegeben worden sein
 * @author Michael
 */
public class Karte 
{
  protected int nummer = -1;
  protected String wortEins = null;
  protected String wortZwei = null;
  protected boolean richtung = true;
  protected boolean grossKleinschreibung = false;

  protected Hashtable<String, String> fehler = null;
  
  public Karte() {
  }

  public Karte(int nummer, String wortEins, String wortZwei, boolean richtung, boolean grossKleinschreibung) {
  	this.nummer = nummer;
  	this.setWortEins(wortEins);
  	this.setWortZwei(wortZwei);
  	this.richtung = richtung;
  	this.grossKleinschreibung = grossKleinschreibung;
  }
  
  /**
   * Kontrolliert ob wortEins und wortZwei eingegeben wurden
   */
  public void validiere() {
  	fehler = null;
  	if (wortEins == null || wortEins.length() == 0) {
  		fehler = new Hashtable();
  		fehler.put("wortEins", "Muss eingegeben werden");
  	}
  	if (wortZwei == null || wortZwei.length() == 0) {
  		if (fehler == null)
  			fehler = new Hashtable();
  		fehler.put("wortZwei", "Muss eingegeben werden");
  	}
  }
  
  @Override
  public boolean equals(Object o) {
  	boolean ret = false;
  	if (o != null && o instanceof Karte) {
  		Karte k = (Karte)o;
  		if (k.nummer == nummer && 
  				(k.wortEins == null && wortEins == null || k.wortEins.equals(wortEins)) &&
  				(k.wortZwei == null && wortZwei == null || k.wortZwei.equals(wortZwei)))
  			ret = true;
  	}
  	return ret;
  }
  
  public Hashtable<String, String> getFehler() {
  	return fehler;
  }
  
  @Override
  public String toString() {
  	return nummer + " " + wortEins + " " + wortZwei + " " + grossKleinschreibung + " " + richtung;
  }
  
  /**
   * Liefert in Abh�ngigkeit der eingestellten Fragerichtung und in Abh�ngigkeit ob die Gro�-/Kleinschreibung
   * ber�cksichtigt werden soll ob das eingegebene Wort dem gesuchten Wort entspricht
   * @param wort
   * @return
   */
  public boolean getRichtig(String wort) {
  	boolean ret = false;
  	if (wort != null)
	  	if (richtung)
	  		// wortEins ist bekannt, wortZwei muss eingegeben werden
	  		if (grossKleinschreibung) {
	    		if (wort.equals(this.wortZwei))
	    			ret = true;
	  		} else {
	    		if (wort.toLowerCase().equals(this.wortZwei.toLowerCase()))
	    			ret = true;
	  		}
	  	else 
	  		// wortZwei ist bekannt, wortEins muss eingegeben werden
	  		if (grossKleinschreibung) {
	    		if (wort.equals(this.wortEins))
	    			ret = true;
	  		} else {
	    		if (wort.toLowerCase().equals(this.wortEins.toLowerCase()))
	    			ret = true;
	  		}
  	return ret;
  }

	public int getNummer() {
		return nummer;
	}
	public String getWortEins() {
		return wortEins;
	}
	public void setWortEins(String wortEins) {
		this.wortEins = wortEins;
		if (this.wortEins != null)
			this.wortEins = this.wortEins.trim();
	}
	public String getWortZwei() {
		return wortZwei;
	}
	public void setWortZwei(String wortZwei) {
		this.wortZwei = wortZwei;
		if (this.wortZwei != null)
			this.wortZwei = this.wortZwei.trim();
	}
	public boolean getRichtung() {
		return richtung;
	}
	public boolean getGrossKleinschreibung() {
		return grossKleinschreibung;
	}
}