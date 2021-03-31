package br.com.prjRafael.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Util {
		
	public static byte[] toByteArray (String end){
		
		Path pdfPath = Paths.get(end);
		byte[] pdf = null;
		try {
			pdf = Files.readAllBytes(pdfPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("convertido");
		return pdf;
	}
	
	public static File byteArrayToDoc(byte[] bArray) {
		
		File file = new File("C:\\laudoTemp\\"+gerarNumero(3)+".doc");
		
		try {
			OutputStream out = new FileOutputStream(file);
			out.write(bArray);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return file;
		
	}
		
	public static String gerarNumero(Integer size){
		
		Random r = new Random();
		String n = "";
		for(int i=0;i<=size;i++){
			n = n+String.valueOf(Math.abs(r.nextInt()));
		}		
		return n;
	}
	
	public static Calendar timestampToCalendar(Timestamp timestamp) {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(timestamp);
	    return cal;
	}
	
	public static String formatDatePostgreSQL(Date date) {
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
		return dt.format(date);
	}

	public static String formatDateTimePostgreSQL(Date date) {
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dt.format(date);
	}

	public static String formatDate(Date date) {
		SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
		return dt.format(date);		
	}

	public static String formatDateFirebird(Date date) {
		SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
		return dt.format(date);
	}

	public static String formatDateTimeFirebird(Date date) {
		SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		return dt.format(date);
	}
	
	public static String formatTime(Date date) {
		SimpleDateFormat dt = new SimpleDateFormat("HH:mm:ss");
		return dt.format(date);
	}	
	
	public static String formatDateReport(Date date) {
		SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
		return dt.format(date);
	}

	public static String formatDateTimeReport(Date date) {
		SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return dt.format(date);
	}

	public static Timestamp timeToTimestamp(Time time){
		
		String df = "2000-01-01 " +time.toString();
		
		return Timestamp.valueOf(df);
	}
	
	@SuppressWarnings("deprecation")
	public static Timestamp timestampToDate(Timestamp timestamp){
		
		Date date = new Date(timestamp.getYear(), timestamp.getMonth(), timestamp.getDate());
		String df =  formatDatePostgreSQL(date) + " 00:00:00";
		
		return Timestamp.valueOf(df);
	}
	
	public static Timestamp joinDateTime(Timestamp date, Time time){
		
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd ");
		String data = dt.format(date);
		
		String df = data +time.toString();
		
		return Timestamp.valueOf(df);
	}

	public static String quotedStr(String str) {
		return "'" + str + "'";
	}
	
	public static java.sql.Date utilDateToSqlDate(Date data){
		if (data == null) return null;
		
		return new java.sql.Date(data.getTime());
	}
	
	public static String md5(String senha) {
		return md5(senha, 16);
	}
	
	public static String md5(String senha, int radix) {
		String sen = "";
		MessageDigest md = null;
		
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));
				
		sen = hash.toString(radix);
		return sen;
	}

	public static Boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		
		int columns = rsmd.getColumnCount();
		
		for (int x=1; x<=columns; x++) {
			if (columnName.equals(rsmd.getColumnName(x))) {
				return true;
			}
		}
		
		return false;
	}
	
	public static Integer returnValueId(ResultSet rs, String columnName) throws SQLException{
		if (hasColumn(rs, columnName))
			return rs.getInt(columnName);
					
		return null;
	}
	
	public static String returnValueString(ResultSet rs, String columnName) throws SQLException{
		if (hasColumn(rs, columnName))
			return rs.getString(columnName);
					
		return null;
	}

	public static Integer returnNull(Integer check) {
		
		if (check == null) check = 0;
		
		if (check > 0) {
			return check;
		} else {
			return null;
		}
	}

	public static Boolean isIntegerEmpty(Integer check) {
		
		if (check == null) check = 0;
		
		if (check == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public static String prepareFilter(String string) {
		
		if (string == null) return "";
		
		if (string.trim() == "") return "";

		return string + "%";
	}
	
	public static Boolean testArrayList(ArrayList<?> ar) {
		
		if (ar == null) return false;
		
		if (ar.size() == 0) return false;
		
		return true;
	}
	
	public static String arrayToSQLString(ArrayList<String> ar) {
		String r = "";
		
		for (Integer i=0;i<ar.size();i++) {
			r += Util.quotedStr(ar.get(i));
			
			
			if (i < (ar.size()-1))
				r += ", ";
			
		}
		
		return r;
	}
	
	public static String arrayToSQLInteger(ArrayList<Integer> ar) {
		String r = "";
		
		for (Integer i=0;i<ar.size();i++) {
			r += ar.get(i);
			
			
			if (i < (ar.size()-1))
				r += ", ";
			
		}
		
		return r;
	}
	
	public static Map<String, Object> getObjectListMap(List<Map<String, Object>> list, 
			String key, Object value) {
		for (int x=0;x<list.size();x++) {
			if (list.get(x).get(key).equals(value)) {
				return list.get(x); 
			}
		}
		
		return null;
	}
	
	public static String LPad(String str, Integer length, char car) {
		if (str.length() == length) 
			return str;
		
		  return str + 
		         String.format("%" + (length - str.length()) + "s", "")
		                     .replace(" ",  String.valueOf(car));
	}

	public static String RPad(String str, Integer length, char car) {
		if (str.length() == length) 
			return str;

		return String.format("%" + (length - str.length()) + "s", "")
	               .replace(" ", String.valueOf(car)) 
	         + str;
	}
	
	public static String diaSemana(int idx) {
		String diaSemana = "";
		
		switch (idx) {
			case Calendar.SUNDAY:
	            diaSemana = "Domingo";
				break;
			case Calendar.MONDAY:
	            diaSemana = "Segunda-Feira";
				break;
			case Calendar.TUESDAY:
	            diaSemana = "Terça-Feira";
				break;
			case Calendar.WEDNESDAY:
	            diaSemana = "Quarta-Feira";
				break;
			case Calendar.THURSDAY:
	            diaSemana = "Quinta-Feira";
				break;
			case Calendar.FRIDAY:
	            diaSemana = "Sexta-Feira";
				break;
			case Calendar.SATURDAY:
	            diaSemana = "Sábado";
				break;
			default:
				break;
		}	
		
		return diaSemana;
	}
	
	public static Integer coalesce(Integer param, Integer value) {
		if (param == null) {
			return value;
		} else {
			return param;
		}					
	}

	public static String mesExtenso(int idx) {
		String mes = "";
		
		switch (idx) {
			case Calendar.JANUARY:
				mes = "Janeiro";
				break;
			case Calendar.FEBRUARY:
				mes = "Fevereiro";
				break;
			case Calendar.MARCH:
				mes = "Março";
				break;
			case Calendar.APRIL:
				mes = "Abril";
				break;
			case Calendar.MAY:
				mes = "Maio";
				break;
			case Calendar.JUNE:
				mes = "Junho";
				break;
			case Calendar.JULY:
				mes = "Julho";
				break;
			case Calendar.AUGUST:
				mes = "Agosto";
				break;
			case Calendar.SEPTEMBER:
				mes = "Setembro";
				break;
			case Calendar.OCTOBER:
				mes = "Outubro";
				break;
			case Calendar.NOVEMBER:
				mes = "Novembro";
				break;
			case Calendar.DECEMBER:
				mes = "Dezembro";
				break;
			default:
				break;
		}	
		
		return mes;
	}
	
	public static String mesAbreviado(int idx) {
		String mes = "";
		
		switch (idx) {
			case Calendar.JANUARY:
				mes = "JAN";
				break;
			case Calendar.FEBRUARY:
				mes = "FEV";
				break;
			case Calendar.MARCH:
				mes = "MAR";
				break;
			case Calendar.APRIL:
				mes = "ABR";
				break;
			case Calendar.MAY:
				mes = "MAI";
				break;
			case Calendar.JUNE:
				mes = "JUN";
				break;
			case Calendar.JULY:
				mes = "JUL";
				break;
			case Calendar.AUGUST:
				mes = "AGO";
				break;
			case Calendar.SEPTEMBER:
				mes = "SET";
				break;
			case Calendar.OCTOBER:
				mes = "OUT";
				break;
			case Calendar.NOVEMBER:
				mes = "NOV";
				break;
			case Calendar.DECEMBER:
				mes = "DEZ";
				break;
			default:
				break;
		}	
		
		return mes;
	}
	
	public static String firstCharUp(String name){
        return name.replace(name.charAt(0), (char)Character.toUpperCase(name.charAt(0)));
	}

	public static String leadingZeros(int num, int digits) {
		String output = Integer.toString(num);
	    while (output.length() < digits) output = "0" + output;
	    return output;
	}
	
	public static String calcularIdade(Timestamp dataNascimento){
		
		Calendar cData = Calendar.getInstance();  
        Calendar cHoje= Calendar.getInstance();  
        cData.setTime(dataNascimento);  
        cData.set(Calendar.YEAR, cHoje.get(Calendar.YEAR));  
        int idade = cData.after(cHoje) ? -1 : 0;  
        cData.setTime(dataNascimento);  
        idade += cHoje.get(Calendar.YEAR) - cData.get(Calendar.YEAR);  
        return Integer.toString(idade);
	}
	
	 public static Double calcularIMC(Double peso, Double altura){
		 Double retorno = 0.0;
		 
		 retorno =  (peso/(altura*altura));
		 
		 return retorno;
	 }
	 
	 public static String resultadoIMC(float imc){
		 String result;
		 if (imc <= 19)
			 result = "Abaixo do Peso";
		 else if (imc <= 25)
			result = "Peso ideal";
		else if (imc <= 30)
			result = "Acima do Peso";
		else if (imc <= 35)
			result = "Obesidade Leve";
		else 
			result = "Obesidade"; 
	 return result;
	 }
	 
	public static String randomNumber() {

		Random random = new Random();

		Integer randomRetorno = random.nextInt(999999);

		return new Integer(randomRetorno).toString();
	}
		
	public static String retiraCaracteresEspeciais(String stringFonte) {
		String passa = stringFonte;
		passa = passa.replaceAll("[ÂÀÁÄÃ]", "A");
		passa = passa.replaceAll("[âãàáä]", "a");
		passa = passa.replaceAll("[ÊÈÉË]", "E");
		passa = passa.replaceAll("[êèéë]", "e");
		passa = passa.replaceAll("ÎÍÌÏ", "I");
		passa = passa.replaceAll("îíìï", "i");
		passa = passa.replaceAll("[ÔÕÒÓÖ]", "O");
		passa = passa.replaceAll("[ôõòóö]", "o");
		passa = passa.replaceAll("[ÛÙÚÜ]", "U");
		passa = passa.replaceAll("[ûúùü]", "u");
		passa = passa.replaceAll("Ç", "C");
		passa = passa.replaceAll("ç", "c");
		passa = passa.replaceAll("[ýÿ]", "y");
		passa = passa.replaceAll("Ý", "Y");
		passa = passa.replaceAll("ñ", "n");
		passa = passa.replaceAll("Ñ", "N");
		passa = passa.replaceAll(":", "");
		passa = passa.replaceAll("[-+=*&amp;%$#@!_]", "");
		passa = passa.replaceAll("['\"]", "");
		passa = passa.replaceAll("[<>()\\{\\}]", "");
		passa = passa.replaceAll("['\\\\.,()|/]", "");
		passa = passa.replaceAll("[^!-ÿ]{1}[^ -ÿ]{0,}[^!-ÿ]{1}|[^!-ÿ]{1}", "");
		return passa;
	}
	 
	public static String createNameReport() {
		Timestamp dataHora = new Timestamp(System.currentTimeMillis());
		
		String sDataHora = dataHora.toString();
		sDataHora = Util.retiraCaracteresEspeciais(sDataHora);
		String nomeArquivo = sDataHora;
		nomeArquivo = md5(nomeArquivo);
		
		return nomeArquivo;
	}
	
	public static void createFolder(String path){
		
		File file = new File(path);
		
		if(!file.exists()){
			file.mkdir();
		}
	}	

}
