/*
 * Saldini
 * 19631210
 * 5M Bjb Reg Pagi
 */
package sewapc;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import java.io.File;
import net.sf.jasperreports.engine.JasperCompileManager; 
import net.sf.jasperreports.engine.JasperFillManager; 
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery; 
import net.sf.jasperreports.engine.design.JasperDesign; 
import net.sf.jasperreports.engine.xml.JRXmlLoader; 
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Acer
 */
public class configDB {
    
    private String url = "jdbc:mysql://localhost:3306/sewa_pc";
    private String user = "root";
    private String pass = "";
    
    public configDB(){}
    
    public Connection getKoneksi() throws SQLException{
        
        try {
            Driver driverku = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(driverku);
            System.out.println("Database Berhasil Dikoneksikan");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return DriverManager.getConnection(url, user, pass);
    }
        
    //============================== DINAMIS ======================================================
    public boolean getDuplikasiKey(String tabel, String Primary, String isi){
        boolean hasil = false;
        try {
            String SQLCari = "SELECT*FROM "+tabel+" WHERE "+Primary+" = '"+isi+"'";
            Statement cekData = getKoneksi().createStatement();
            ResultSet pk = cekData.executeQuery(SQLCari);
            
            if (pk.next()){
                hasil = true;
            }else{
                hasil = false;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return hasil;
    }   
    
    public String getFieldArray(String[] Fieldnya){
        String hasil = "";
        int deteksi = Fieldnya.length - 1; // deteksi array terakhir
        try {
            for (int i = 0; i < Fieldnya.length; i++) {
                if (i==deteksi){
                    hasil = hasil + Fieldnya[i];
                }else{
                  hasil = hasil + Fieldnya[i]+",";   
                }
               
            }
        } catch (Exception e) {
            System.out.println( e.toString());
        }
        
        return "("+hasil+")";
    }
    
    public String getValueFieldArray(String[] Valuenya){
        String hasil = "";
        int deteksi = Valuenya.length - 1;
        try {
            for (int i = 0; i < Valuenya.length; i++) {
                if (i == deteksi)
                {
                    hasil = hasil +"'"+Valuenya[i]+"'";
                }else{
                    hasil = hasil +"'"+Valuenya[i]+"',";
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
         return "("+hasil+")";        
    }
    
    public String getCombineValueFieldArray(String[] Fieldnya, String[] Valuenya){
        String hasil = "";
        int deteksi = Fieldnya.length - 1;
        try {
             for (int i = 0; i < Fieldnya.length; i++) {
                 if (i == deteksi){
                     hasil = hasil+Fieldnya[i]+"='"+Valuenya[i]+"'";
                 }else{
                     hasil = hasil+Fieldnya[i]+"='"+Valuenya[i]+"',";
                 }
                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        
        return hasil;
    }
    
    public void UbahDinamis(String Tabelnya, String Primarynya, String isiPrimary, String[] Fieldnya, String[] Valuenya){
        
        try {
           String SQLUpdate ="UPDATE "+Tabelnya+" SET "+getCombineValueFieldArray(Fieldnya, Valuenya)+" WHERE "+Primarynya+"='"+isiPrimary+"'"; 
           Statement perintah = getKoneksi().createStatement();
           perintah.executeUpdate(SQLUpdate);
           perintah.close();
           getKoneksi().close();
           JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        
    }
    
    public void SimpanDinamis(String Tabelnya, String[] Fieldnya, String[] Valuenya){
        try {
            String SQLSimpan ="INSERT INTO "+Tabelnya+" "+getFieldArray(Fieldnya)+" VALUES "+getValueFieldArray(Valuenya);
            Statement perintah = getKoneksi().createStatement();
            perintah.executeUpdate(SQLSimpan);
            perintah.close();
            getKoneksi().close();
            JOptionPane.showMessageDialog(null,"Data Berhasil disimpan");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    
    public void HapusDinamis(String tabel, String Primary, String isi){
        try {
          String SQLHapus = "DELETE FROM "+tabel+" WHERE "+Primary+" = '"+isi+"'";
          Statement perintahHapus = getKoneksi().createStatement();
          perintahHapus.executeUpdate(SQLHapus);
          perintahHapus.close();
          getKoneksi().close();
          JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    
    public void setJudulKolom(JTable Tabelnya,String[] JudulKolom){
        try {
           DefaultTableModel model = new DefaultTableModel();  
           Tabelnya.setModel(model);
             for (int i = 0; i < JudulKolom.length; i++) {
                model.addColumn(JudulKolom[i]);
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
    
    public void setLebarJudulKolom(JTable Tabelnya, int[] LebarKolomKe){
        try {
            TableColumn Kolomke = new TableColumn();
            Tabelnya.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
                       
            for (int i = 0; i < LebarKolomKe.length; i++) {
                Kolomke = Tabelnya.getColumnModel().getColumn(i);
                Kolomke.setPreferredWidth(LebarKolomKe[i]);
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
    
    public Object[][] isiTabel(String SQL,int jumlah){
        Object[][] data = null;
        try {
           Statement perintah = getKoneksi().createStatement();
           ResultSet dataset  = perintah.executeQuery(SQL);
           dataset.last();
           int baris = dataset.getRow();
           dataset.beforeFirst();
           data = new Object[baris][jumlah];
           int j = 0;
           
            while (dataset.next()) {                
                for (int i = 0; i < jumlah; i++) {
                   data[j][i]= dataset.getString(i+1);
                }
                j++;
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        
        return data;
        
    }
    
    public void setTampilTabel(JTable Tabelnya,String[] Judul,String SQL){
        try {
            Tabelnya.setModel(new DefaultTableModel(isiTabel(SQL,Judul.length), Judul));
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
    
    public void cariData(JTable Tabelnya,String[] JudulKolom, String SQLCari){
        try {
            Tabelnya.setModel(new DefaultTableModel(isiTabel(SQLCari,JudulKolom.length), JudulKolom));
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
    
    public void tampilLaporan(String laporanFile, String SQL){
           try {
                 File file = new File(laporanFile);
                 JasperDesign jasDes = JRXmlLoader.load(file);
    
                JRDesignQuery sqlQuery = new JRDesignQuery();
                sqlQuery.setText(SQL);
                jasDes.setQuery(sqlQuery);
    
                JasperReport JR = JasperCompileManager.compileReport(jasDes);
                JasperPrint JP = JasperFillManager.fillReport(JR,null,getKoneksi()); 
                JasperViewer.viewReport(JP);
             } catch (Exception e) {
                 JOptionPane.showMessageDialog(null,e.toString());       
    
              }
         }
    
}
