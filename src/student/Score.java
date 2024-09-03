/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package student;

import db.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author MIS
 */
public class Score {
    Connection con = MyConnection.getConnection();
    PreparedStatement ps;
    
    public int getMax() {
        int id = 0;
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select max(id) from score");
            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id + 1;
    }
    
    public boolean getDetails(int sid,int semesterNo){
        try {
            ps = con.prepareStatement("select * from course where student_id = ? and semester = ?");
            ps.setInt(1,sid);
            ps.setInt(2,semesterNo);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Home.course12.setText(rs.getString(4));
                Home.course22.setText(rs.getString(5));
                Home.course32.setText(rs.getString(6));
                Home.course42.setText(rs.getString(7));
                return true;
            }else {
                JOptionPane.showMessageDialog(null, "Student id doesn't exist");
            }
        } catch (SQLException ex){
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean isSidSemesterExist(int sid,int semesterNo){
        try {
            ps = con.prepareStatement("select * from score where student_id = ? and semester = ?");
            ps.setInt(1, sid);
            ps.setInt(2, semesterNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public void insert(int id, int sid, int semester, 
                   String course1, String course2, String course3, String course4,
                   double score1, double score2, double score3, double score4, double average) {
    String sql = "INSERT INTO score (id, student_id, semester, course1, score1, course2, score2, course3, score3, course4, score4, average) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try {
        ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ps.setInt(2, sid);
        ps.setInt(3, semester);
        ps.setString(4, course1);
        ps.setDouble(5, score1);
        ps.setString(6, course2);
        ps.setDouble(7, score2);
        ps.setString(8, course3);
        ps.setDouble(9, score3);
        ps.setString(10, course4);
        ps.setDouble(11, score4);
        ps.setDouble(12, average);
        if (ps.executeUpdate() > 0) {
            JOptionPane.showMessageDialog(null, "Score added successfully");
        }
    } catch (SQLException ex) {
        Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
    }
}
    
    public void getScoreValue(JTable table, String searchValue) {
        String sql = "select * from score c join student s "
                + "on c.student_id = s.id "
                + "where concat(s.name,s.email,s.phone) like ? order by c.id asc, s.id asc";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + searchValue + "%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[12];
                row[0] = rs.getInt(1);
                row[1] = rs.getInt(2);
                row[2] = rs.getInt(3);
                row[3] = rs.getString(4);
                row[4] = rs.getDouble(5);
                row[5] = rs.getString(6);
                row[6] = rs.getDouble(7);
                row[7] = rs.getString(8);
                row[8] = rs.getDouble(9);
                row[9] = rs.getString(10);
                row[10] = rs.getDouble(11);
                row[11] = rs.getDouble(12);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(int sid, int semNo, double score1, double score2, double score3, double score4,double average){
        String sql = "update score set score1=?,score2=?,score3=?,score4=?,average=? where student_id=? and semester=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setDouble(1, score1);
            ps.setDouble(2, score2);
            ps.setDouble(3, score3);
            ps.setDouble(4, score4);
            ps.setDouble(5, average);
            ps.setInt(6, sid);
            ps.setInt(7, semNo);
            if (ps.executeUpdate()>0){
                JOptionPane.showMessageDialog(null,"Score updated successfully");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public double getAverage(int id) {
    String sql = "select avg(average) as avg_score from score where student_id = ?";
    double avgScore = 0.0;
    try {
        ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            avgScore = rs.getDouble("avg_score");
        }
        
    } catch (SQLException ex) {
        Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return avgScore;
}
}
