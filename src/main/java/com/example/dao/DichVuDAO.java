package com.example.dao;

import java.util.ArrayList;
import java.util.List;
import com.example.model.*;
import com.example.util.*;

import java.sql.*;

public class DichVuDAO {
    private static DichVuDAO instance;
    private DichVuDAO() {}
    public static DichVuDAO getInstance() {
        if (instance == null) {
            instance = new DichVuDAO();
        }
        return instance;
    }
    public List<DichVu> LayDSDVDA(){
        List<DichVu> list = new ArrayList<DichVu>();
        String sql = "SELECT * FROM View_DichVuDoAn";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                DichVu dv = new DichVu(
                        rs.getString("MaDV"),
                        rs.getString("TenDV"),
                        rs.getBoolean("BestSeller"),
                        rs.getInt("DonGia"),
                        rs.getString("TrangThai")
                );
                list.add(dv);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public void ThemDVDA(String tenDV, Boolean bestSeller, int donGia, String trangThai) {
        //use proc_ThemDVDoAn
        String sql = "EXEC proc_ThemDVDoAn ?, ?, ?, ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tenDV);
            stmt.setBoolean(2, bestSeller);
            stmt.setInt(3, donGia);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void SuaDVDoAn(String maDV, String tenDV, Boolean bestSeller, int donGia, String trangThai) {
        //use proc_SuaDVDoAn
        String sql = "EXEC proc_SuaDVDoAn ?, ?, ?, ?, ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maDV);
            stmt.setString(2, tenDV);
            stmt.setBoolean(3, bestSeller);
            stmt.setInt(4, donGia);
            stmt.setString(5, trangThai);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void XoaDV(String maDV) {
        //use proc_XoaDV
        String sql = "EXEC proc_XoaDV ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maDV);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}