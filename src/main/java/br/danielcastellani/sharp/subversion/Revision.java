/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.sharp.subversion;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author DanCastellani
 */
public class Revision implements Comparable<Revision> {

    public int number;
    public Calendar date;

    public Revision() {
    }

    @Override
    public int compareTo(Revision o) {
        return this.number - o.number;
    }

    @Override
    public String toString() {
        return number + date.toString();
    }

    public String getFormatedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date.getTime());
    }
}
