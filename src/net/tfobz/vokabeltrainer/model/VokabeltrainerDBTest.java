package net.tfobz.vokabeltrainer.model;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;

public class VokabeltrainerDBTest
{
	@Test
	public void getLernkarteiTest() {
		VokabeltrainerDB.loeschenTabellen();
		VokabeltrainerDB.erstellenTabellen();
		VokabeltrainerDB.hinzufuegenTestdaten();
		assertNotNull(VokabeltrainerDB.getLernkartei(1));
		assertNotNull(VokabeltrainerDB.getLernkartei(2));
		assertNotNull(VokabeltrainerDB.getLernkartei(3));
		assertNull(VokabeltrainerDB.getLernkartei(4));
	}
	@Test
	public void getLernkarteienTest() {
		VokabeltrainerDB.loeschenTabellen();
		VokabeltrainerDB.erstellenTabellen();
		assertEquals(0, VokabeltrainerDB.getLernkarteien().size());
		VokabeltrainerDB.hinzufuegenTestdaten();
		assertEquals(3, VokabeltrainerDB.getLernkarteien().size());
	}
	@Test
	public void getFaecherTest() {
		VokabeltrainerDB.loeschenTabellen();
		VokabeltrainerDB.erstellenTabellen();
		VokabeltrainerDB.hinzufuegenTestdaten();
		assertEquals(null, VokabeltrainerDB.getFaecher(0));
		assertEquals(1, VokabeltrainerDB.getFaecher(1).size());
		assertEquals(0, VokabeltrainerDB.getFaecher(3).size());
	}
	@Test
	public void getFachTest() {
		VokabeltrainerDB.loeschenTabellen();
		VokabeltrainerDB.erstellenTabellen();
		VokabeltrainerDB.hinzufuegenTestdaten();
		assertNull(VokabeltrainerDB.getFach(1, 3));
		assertEquals(1, VokabeltrainerDB.getFach(1, 1).getNummer());
	}
	@Test
	public void getFachNummerFachTest() {
		VokabeltrainerDB.loeschenTabellen();
		VokabeltrainerDB.erstellenTabellen();
		VokabeltrainerDB.hinzufuegenTestdaten();
		assertNull(VokabeltrainerDB.getFach(3));
		assertEquals(1, VokabeltrainerDB.getFach(1).getNummer());
	}
	@Test
	public void getKarteTest() {
		VokabeltrainerDB.loeschenTabellen();
		VokabeltrainerDB.erstellenTabellen();
		VokabeltrainerDB.hinzufuegenTestdaten();
		assertNull(VokabeltrainerDB.getZufaelligeKarte(1, 3));
		Fach f = VokabeltrainerDB.getFach(1, 1);
		f.setGelerntAm(VokabeltrainerDB.getDateOneDayBeforeToday());
		assertEquals(0, VokabeltrainerDB.aendernFach(f));
		assertNotNull(VokabeltrainerDB.getZufaelligeKarte(1, 1));
		f = VokabeltrainerDB.getFach(1, 1);
		assertEquals(VokabeltrainerDB.convertToString(
				VokabeltrainerDB.getDateOneDayBeforeToday()), f.getGelerntAmString());
	}
	@Test
	public void aendernFachTest() {
		VokabeltrainerDB.loeschenTabellen();
		VokabeltrainerDB.erstellenTabellen();
		VokabeltrainerDB.hinzufuegenTestdaten();
		Fach f = VokabeltrainerDB.getFach(1, 1);
		f.setGelerntAm(VokabeltrainerDB.getDateOneDayBeforeToday());
		f.setBeschreibung("x");
		f.setErinnerungsIntervall(3);
		assertEquals(0, VokabeltrainerDB.aendernFach(f));
		f = VokabeltrainerDB.getFach(1, 1);
		assertEquals("x", f.getBeschreibung());
		assertEquals(3, f.getErinnerungsIntervall());
		assertEquals(VokabeltrainerDB.convertToString(VokabeltrainerDB.getDateOneDayBeforeToday()),
				f.getGelerntAmString());
		f.setBeschreibung(null);
		assertEquals(-2, VokabeltrainerDB.aendernFach(f));
	}
	@Test
	public void setKarteRichtigTest() {
		VokabeltrainerDB.loeschenTabellen();
		VokabeltrainerDB.erstellenTabellen();
		VokabeltrainerDB.hinzufuegenTestdaten();
		Karte k = VokabeltrainerDB.getZufaelligeKarte(1, 1);
		assertEquals(-2, VokabeltrainerDB.setKarteRichtig(k));
		Fach f = new Fach();
		assertEquals(0, VokabeltrainerDB.hinzufuegenFach(1,f));
		f = VokabeltrainerDB.getFach(3);
		assertEquals(0, VokabeltrainerDB.setKarteRichtig(k));
		assertEquals(null, VokabeltrainerDB.getZufaelligeKarte(1, 1));
		assertEquals(k, VokabeltrainerDB.getZufaelligeKarte(1, 3));
	}
	@Test
	public void setKarteRichtigTest1() {
		VokabeltrainerDB.loeschenTabellen();
		VokabeltrainerDB.erstellenTabellen();
		VokabeltrainerDB.hinzufuegenTestdaten();
		Fach f = VokabeltrainerDB.getFach(1);
		f.setGelerntAm(VokabeltrainerDB.getDateOneDayBeforeToday());
		VokabeltrainerDB.aendernFach(f);
		f = VokabeltrainerDB.getFach(1);
		assertEquals(
				VokabeltrainerDB.convertToString(VokabeltrainerDB.getDateOneDayBeforeToday()), f.getGelerntAmString());
		Fach f1 = new Fach();
		assertEquals(0,VokabeltrainerDB.hinzufuegenFach(1, f1));
		Karte k = VokabeltrainerDB.getZufaelligeKarte(1, 1);
		assertEquals(0, VokabeltrainerDB.setKarteRichtig(k));
		f = VokabeltrainerDB.getFach(1);
		assertEquals(
				VokabeltrainerDB.convertToString(new Date()), f.getGelerntAmString());
	}
	@Test
	public void setKarteFalschTest() {
		VokabeltrainerDB.loeschenTabellen();
		VokabeltrainerDB.erstellenTabellen();
		VokabeltrainerDB.hinzufuegenTestdaten();
		Karte k = VokabeltrainerDB.getZufaelligeKarte(1, 1);
		Fach f = new Fach();
		assertEquals(0, VokabeltrainerDB.hinzufuegenFach(1,f));
		f = VokabeltrainerDB.getFach(3);
		assertEquals(0, VokabeltrainerDB.setKarteRichtig(k));
		assertEquals(null, VokabeltrainerDB.getZufaelligeKarte(1, 1));
		assertEquals(k, VokabeltrainerDB.getZufaelligeKarte(1, 3));
		assertEquals(0, VokabeltrainerDB.setKarteFalsch(k));
		assertEquals(k, VokabeltrainerDB.getZufaelligeKarte(1, 1));
		assertEquals(null, VokabeltrainerDB.getZufaelligeKarte(1, 3));
	}
	@Test
	public void setKarteFalschTest1() {
		VokabeltrainerDB.loeschenTabellen();
		VokabeltrainerDB.erstellenTabellen();
		VokabeltrainerDB.hinzufuegenTestdaten();
		Fach f = VokabeltrainerDB.getFach(1);
		f.setGelerntAm(VokabeltrainerDB.getDateOneDayBeforeToday());
		VokabeltrainerDB.aendernFach(f);
		f = VokabeltrainerDB.getFach(1);
		assertEquals(
				VokabeltrainerDB.convertToString(VokabeltrainerDB.getDateOneDayBeforeToday()), f.getGelerntAmString());
		Karte k = VokabeltrainerDB.getZufaelligeKarte(1, 1);
		assertEquals(0, VokabeltrainerDB.setKarteFalsch(k));
		f = VokabeltrainerDB.getFach(1);
		assertEquals(
				VokabeltrainerDB.convertToString(new Date()), f.getGelerntAmString());
	}
	@Test
	public void hinzufuegenFachTest() {
		VokabeltrainerDB.loeschenTabellen();
		VokabeltrainerDB.erstellenTabellen();
		VokabeltrainerDB.hinzufuegenTestdaten();
		Fach f = new Fach();
		assertEquals(0, VokabeltrainerDB.hinzufuegenFach(1, f));
		assertEquals(3, f.getNummer());
		f = VokabeltrainerDB.getFach(3);
		assertEquals("Fach 2", f.getBeschreibung());
		assertEquals(1, f.getErinnerungsIntervall());
		assertEquals(VokabeltrainerDB.getActualDate(), f.getGelerntAmString());
		assertEquals(-1, VokabeltrainerDB.hinzufuegenFach(1, f));
		f = new Fach();
		f.setGelerntAm(VokabeltrainerDB.getDateOneDayBeforeToday());
		assertEquals(0, VokabeltrainerDB.hinzufuegenFach(1, f));
		assertEquals(-1, VokabeltrainerDB.hinzufuegenFach(10, f));
		f = VokabeltrainerDB.getFach(4);
		assertEquals(
				VokabeltrainerDB.convertToString(VokabeltrainerDB.getDateOneDayBeforeToday()),
				VokabeltrainerDB.convertToString(f.getGelerntAm()));
	}
	@Test
	public void hinzufuegenLernkarteiTest() {
		VokabeltrainerDB.loeschenTabellen();
		VokabeltrainerDB.erstellenTabellen();
		VokabeltrainerDB.hinzufuegenTestdaten();
		Lernkartei l = new Lernkartei();
		assertEquals(-2, VokabeltrainerDB.hinzufuegenLernkartei(l));
		assertEquals(3, l.getFehler().size());
		l.setBeschreibung("b");
		l.setWortEinsBeschreibung("wb1");
		l.setWortZweiBeschreibung("wb2");
		assertEquals(0, VokabeltrainerDB.hinzufuegenLernkartei(l));
		assertEquals(4, l.getNummer());
		Lernkartei l1 = VokabeltrainerDB.getLernkartei(4);
		assertEquals(l, l1);
		l = new Lernkartei();
		l.setBeschreibung("b");
		l.setWortEinsBeschreibung("wb1");
		l.setWortZweiBeschreibung("wb2");
		assertEquals(-2, VokabeltrainerDB.hinzufuegenLernkartei(l));
		assertEquals(1, l.getFehler().size());
	}
	@Test
	public void aendernLernkarteiTest() {
		VokabeltrainerDB.loeschenTabellen();
		VokabeltrainerDB.erstellenTabellen();
		VokabeltrainerDB.hinzufuegenTestdaten();
		Lernkartei l = VokabeltrainerDB.getLernkartei(1);
		l.setBeschreibung(null);
		assertEquals(-2, VokabeltrainerDB.aendernLernkartei(l));
		Lernkartei l1 = VokabeltrainerDB.getLernkartei(2);
		l.setBeschreibung(l1.getBeschreibung());
		assertEquals(-2, VokabeltrainerDB.aendernLernkartei(l));
		l.setBeschreibung("b2");
		l.setWortEinsBeschreibung("w1");
		l.setWortZweiBeschreibung("w2");
		assertEquals(0, VokabeltrainerDB.aendernLernkartei(l));
		l1 = VokabeltrainerDB.getLernkartei(1);
		assertEquals(l, l1);
	}
	@Test
	public void loeschenLernkarteiTest() {
		VokabeltrainerDB.loeschenTabellen();
		VokabeltrainerDB.erstellenTabellen();
		VokabeltrainerDB.hinzufuegenTestdaten();
		assertEquals(-1, VokabeltrainerDB.loeschenLernkartei(-1));
		assertNotNull(VokabeltrainerDB.getFach(1));
		assertEquals(0, VokabeltrainerDB.loeschenLernkartei(1));
		assertNull(VokabeltrainerDB.getFach(1));
	}
	@Test
	public void hinzufuegenKarteTest() {
		VokabeltrainerDB.loeschenTabellen();
		VokabeltrainerDB.erstellenTabellen();
		VokabeltrainerDB.hinzufuegenTestdaten();
		Karte k = new Karte();
		assertEquals(-2, VokabeltrainerDB.hinzufuegenKarte(1, k));
		k.setWortEins("Haus");
		k.setWortZwei("House");
		assertEquals(-5, VokabeltrainerDB.hinzufuegenKarte(1, k));
		k.setWortEins("w1");
		k.setWortZwei("w1");
		assertEquals(-3, VokabeltrainerDB.hinzufuegenKarte(10, k));
		assertEquals(-4, VokabeltrainerDB.hinzufuegenKarte(3, k));
		assertEquals(0, VokabeltrainerDB.hinzufuegenKarte(2, k));
		assertEquals(k, VokabeltrainerDB.getZufaelligeKarte(2, 2));
	}
	@Test
	public void aendernKarteTest() {
		VokabeltrainerDB.loeschenTabellen();
		VokabeltrainerDB.erstellenTabellen();
		VokabeltrainerDB.hinzufuegenTestdaten();
		Karte k = VokabeltrainerDB.getZufaelligeKarte(1,1);
		assertEquals(1, k.getNummer());
		k.setWortEins(null);
		assertEquals(-2, VokabeltrainerDB.aendernKarte(k));
		Karte k1 = new Karte();
		k1.setWortEins("w1");
		k1.setWortZwei("w2");
		assertEquals(0, VokabeltrainerDB.hinzufuegenKarte(1, k1));
		k.setWortEins("w1");
		k.setWortZwei("w2");
		assertEquals(-4, VokabeltrainerDB.aendernKarte(k));
		k.setWortEins("w11");
		k.setWortZwei("w21");
		assertEquals(0, VokabeltrainerDB.aendernKarte(k));
		assertEquals(k,VokabeltrainerDB.getKarte(k.getNummer()));
	}
	@Test
	public void loeschenKarteTest() {
		VokabeltrainerDB.loeschenTabellen();
		VokabeltrainerDB.erstellenTabellen();
		VokabeltrainerDB.hinzufuegenTestdaten();
		assertEquals(-1, VokabeltrainerDB.loeschenKarte(10));
		assertEquals(0, VokabeltrainerDB.loeschenKarte(1));
		assertNull(VokabeltrainerDB.getZufaelligeKarte(1, 1));
	}
	@Test
	public void loeschenAlleFaecherTest() {
		VokabeltrainerDB.loeschenTabellen();
		VokabeltrainerDB.erstellenTabellen();
		VokabeltrainerDB.hinzufuegenTestdaten();
		assertEquals(-1, VokabeltrainerDB.loeschenAlleFaecher(10));
		assertEquals(0, VokabeltrainerDB.loeschenAlleFaecher(1));
		assertEquals(0, VokabeltrainerDB.getFaecher(1).size());
		assertNull(VokabeltrainerDB.getZufaelligeKarte(1,1));
	}
	@Test
	public void getLernkarteienErinnerungTest() {
		VokabeltrainerDB.loeschenTabellen();
		VokabeltrainerDB.erstellenTabellen();
		VokabeltrainerDB.hinzufuegenTestdaten();
		assertEquals(0, VokabeltrainerDB.getLernkarteienErinnerung().size());
		Fach f = VokabeltrainerDB.getFach(1);
		f.setGelerntAm(VokabeltrainerDB.getDateOneDayBeforeToday());
		f.setErinnerungsIntervall(2);
		VokabeltrainerDB.aendernFach(f);
		assertEquals(0, VokabeltrainerDB.getLernkarteienErinnerung().size());
		f.setErinnerungsIntervall(0);
		VokabeltrainerDB.aendernFach(f);
		assertEquals(0, VokabeltrainerDB.getLernkarteienErinnerung().size());
		f.setErinnerungsIntervall(1);
		VokabeltrainerDB.aendernFach(f);
		assertEquals(1, VokabeltrainerDB.getLernkarteienErinnerung().size());
		assertEquals(VokabeltrainerDB.getLernkarteienErinnerung().get(0),
				VokabeltrainerDB.getLernkartei(1));
	}
	@Test
	public void getFaecherErinnerungTest() {
		VokabeltrainerDB.loeschenTabellen();
		VokabeltrainerDB.erstellenTabellen();
		VokabeltrainerDB.hinzufuegenTestdaten();
		assertNull(VokabeltrainerDB.getFaecherErinnerung(10));
		assertEquals(0, VokabeltrainerDB.getFaecherErinnerung(1).size());
		Fach f = VokabeltrainerDB.getFach(1);
		f.setGelerntAm(VokabeltrainerDB.getDateOneDayBeforeToday());
		f.setErinnerungsIntervall(1);
		VokabeltrainerDB.aendernFach(f);
		assertEquals(1, VokabeltrainerDB.getFaecherErinnerung(1).size());
		assertEquals(VokabeltrainerDB.getFaecherErinnerung(1).get(0),
				VokabeltrainerDB.getFach(1));
	}
	@Test
	public void getKartenTest() {
		VokabeltrainerDB.loeschenTabellen();
		VokabeltrainerDB.erstellenTabellen();
		VokabeltrainerDB.hinzufuegenTestdaten();
		assertNull(VokabeltrainerDB.getKarten(10));
		assertEquals(0, VokabeltrainerDB.getKarten(2).size());
		assertEquals(1, VokabeltrainerDB.getKarten(1).size());
	}
	@Test
	public void importierenKartenTest() {
		VokabeltrainerDB.loeschenTabellen();
		VokabeltrainerDB.erstellenTabellen();
		VokabeltrainerDB.hinzufuegenTestdaten();
		assertEquals(-3, VokabeltrainerDB.importierenKarten(10, "imports\\kartenMitFaecher.txt"));
		assertEquals(-2, VokabeltrainerDB.importierenKarten(1, "imports\\kartenMitFaecherxxxx.txt"));
		assertEquals(-1, VokabeltrainerDB.importierenKarten(1, "imports\\kartenOhneFaecherFalschesFormat.txt"));
		assertEquals(1, VokabeltrainerDB.getFaecher(1).size());
		VokabeltrainerDB.importierenKarten(1, "imports\\kartenOhneFaecher.txt");
		assertEquals(1, VokabeltrainerDB.getFaecher(1).size());
		assertEquals(6, VokabeltrainerDB.getKarten(4).size());
		VokabeltrainerDB.loeschenTabellen();
		VokabeltrainerDB.erstellenTabellen();
		VokabeltrainerDB.hinzufuegenTestdaten();
		assertEquals(-1, VokabeltrainerDB.importierenKarten(1, "imports\\kartenMitFaecherFalschesFormat.txt"));
		assertEquals(0, VokabeltrainerDB.importierenKarten(1, "imports\\kartenMitFaecher.txt"));
		assertEquals(3, VokabeltrainerDB.getFaecher(1).size());
		assertEquals(2, VokabeltrainerDB.getKarten(4).size());
		assertEquals(3, VokabeltrainerDB.getKarten(5).size());
		assertEquals(1, VokabeltrainerDB.getKarten(6).size());
		VokabeltrainerDB.loeschenTabellen();
		VokabeltrainerDB.erstellenTabellen();
		VokabeltrainerDB.hinzufuegenTestdaten();
		assertEquals(0, VokabeltrainerDB.importierenKarten(1, "imports\\kartenMitFaecherDoppelteKarten.txt"));
		assertEquals(3, VokabeltrainerDB.getFaecher(1).size());
		assertEquals(2, VokabeltrainerDB.getKarten(3).size());
		assertEquals(3, VokabeltrainerDB.getKarten(4).size());
		assertEquals(1, VokabeltrainerDB.getKarten(5).size());
	}
	@Test
	public void exportierenKartenTest() {
		VokabeltrainerDB.loeschenTabellen();
		VokabeltrainerDB.erstellenTabellen();
		VokabeltrainerDB.hinzufuegenTestdaten();
		assertEquals(-3, VokabeltrainerDB.exportierenKarten(10, "exports\\nix.txt", true));
		assertEquals(-1, VokabeltrainerDB.exportierenKarten(1, "ixpirts\\nix.txt", true));
		assertEquals(-1, VokabeltrainerDB.exportierenKarten(1, null, true));
		assertEquals(0, VokabeltrainerDB.exportierenKarten(1, "exports\\kartenOhneFaecher.txt", false));
		assertEquals(0, VokabeltrainerDB.importierenKarten(1, "imports\\kartenOhneFaecher.txt"));
		assertEquals(0, VokabeltrainerDB.exportierenKarten(1, "exports\\kartenOhneFaecherEins.txt", false));
		VokabeltrainerDB.loeschenTabellen();
		VokabeltrainerDB.erstellenTabellen();
		VokabeltrainerDB.hinzufuegenTestdaten();
		assertEquals(0, VokabeltrainerDB.exportierenKarten(1, "exports\\kartenMitFaecher.txt", true));
		assertEquals(0, VokabeltrainerDB.importierenKarten(1, "imports\\kartenMitFaecher.txt"));
		assertEquals(0, VokabeltrainerDB.exportierenKarten(1, "exports\\kartenMitFaecherEins.txt", true));
	}
	@Test
	public void getStandardLernkarteiTest() {
		VokabeltrainerDB.loeschenTabellen();
		VokabeltrainerDB.erstellenTabellen();
		VokabeltrainerDB.hinzufuegenTestdaten();
		assertNull(VokabeltrainerDB.getEinstellungenStandardLernkartei());
		assertEquals(0, VokabeltrainerDB.setEinstellungenStandardLernkartei(1));
		assertEquals(1, VokabeltrainerDB.getEinstellungenStandardLernkartei().getNummer());
	}
	@Test
	public void setStandardLernkarteiTest() {
		VokabeltrainerDB.loeschenTabellen();
		VokabeltrainerDB.erstellenTabellen();
		VokabeltrainerDB.hinzufuegenTestdaten();
		assertEquals(-1, VokabeltrainerDB.setEinstellungenStandardLernkartei(10));
		assertEquals(0, VokabeltrainerDB.setEinstellungenStandardLernkartei(1));
		assertEquals(1, VokabeltrainerDB.getEinstellungenStandardLernkartei().getNummer());
		assertEquals(0, VokabeltrainerDB.setEinstellungenStandardLernkartei(-1));
		assertNull(VokabeltrainerDB.getEinstellungenStandardLernkartei());
	}
	@Test
	public void setStandardLernkarteienMitErinnerungTest() {
		VokabeltrainerDB.loeschenTabellen();
		VokabeltrainerDB.erstellenTabellen();
		VokabeltrainerDB.hinzufuegenTestdaten();
		assertEquals(0, VokabeltrainerDB.setEinstellungenLernkarteienMitErinnerung(true));
		assertTrue(VokabeltrainerDB.getEinstellungenLernkarteienMitErinnerung());
		assertEquals(0, VokabeltrainerDB.setEinstellungenLernkarteienMitErinnerung(false));
		assertFalse(VokabeltrainerDB.getEinstellungenLernkarteienMitErinnerung());
	}
	@Test
	public void getErinnerungFaelligTest() {
		Fach f = new Fach();
		f.setErinnerungsIntervall(1);
		f.setGelerntAm(new Date());
		assertEquals(false, f.getErinnerungFaellig());
		f.setErinnerungsIntervall(0);
		assertEquals(false, f.getErinnerungFaellig());
		f.setErinnerungsIntervall(1);
		f.setGelerntAm(VokabeltrainerDB.getDateOneDayBeforeToday());
		assertEquals(true, f.getErinnerungFaellig());
	}
}
