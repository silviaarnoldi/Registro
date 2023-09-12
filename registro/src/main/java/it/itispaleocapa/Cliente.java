
package it.itispaleocapa;
public class Cliente
{
	 String cognome;
    String nome;
    String nazioneNascita;
	 String cittaNascita;
	 String dataNascita;
	 String codiceCliente;
    public Cliente(String c,String n, String nn, String cn, String dn, int cc)
    {
        this.nome=n;
        this.cognome=c;
		  this.nazioneNascita=nn;
        this.cittaNascita=cn;
        this.dataNascita=dn;
        this.codiceCliente=n+c+cc;

    }
}
