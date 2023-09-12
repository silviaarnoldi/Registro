package it.itispaleocapa;
import java.util.Date;
public class Volo
{
	String codiceVolo; 
	String aeroportoPartenza;
	String aeroportoArrivo; 
	Date dataVolo;
	String oraPartenza;
	String oraArrivo;
	String posto;
	int costoVolo;
    public Volo(String cv,String ap, String aa, Date dv,String op,String oa, String p, int c)
    {
        this.codiceVolo=cv;
        this.aeroportoPartenza=ap;
        this.aeroportoArrivo=aa;
        this.dataVolo=dv;
        this.oraPartenza=op;
        this.oraArrivo=oa;
        this.posto=p;
        this.costoVolo=c;
    }
}
