package com.example.dao;
import com.example.util.DatabaseConnection;
import com.example.model.MayTinh;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoanhThuDAO {
    /*
using DataAccessLayer;
using System;
using System.Data;

namespace BusinessAcessLayer
{
    public class DBDT
    {
        private static DBDT instance;
        private DAL db;
        private DBDT()
        {
            db = DAL.Instance;
        }

        public static DBDT Instance
        {
            get
            {
                if (instance == null)
                {
                    instance = new DBDT();
                }
                return instance;
            }
        }

        public DataTable TinhDoanhThu12ThangGanNhat()
        {
            try
            {
                return db.excuteQuery("SELECT * FROM func_TinhDoanhThu12ThangGanNhat()");
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }


        public int TinhTongDoanhThuThang(int thang, int nam)
        {
            try
            {
                object[] param = new object[] { thang, nam };
                var result = db.excuteScalar("SELECT dbo.func_TinhTongDoanhThuThang (@Thang , @Nam)", param);
                return result is int ? (int)result : 0;

            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public int TinhDoanhThuThangNay()
        {
            try
            {
                var result = db.excuteScalar("EXEC proc_TinhDoanhThuThangNay");
                return result is int ? (int)result : 0;
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public DataTable DoanhThuNhanVienTheoThang(int thang, int nam)
        {
            try
            {
                object[] param = new object[] { thang, nam };
                return db.excuteQuery("SELECT * FROM fn_TinhDoanhThuNhanVienTheoThang (@Thang , @Nam) ORDER BY DoanhThu DESC", param);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public DataTable TinhTongTGSDVaDTKH(int thang, int nam)
        {
            try
            {
                object[] param = new object[] { thang, nam };
                return db.excuteQuery("EXEC sp_TinhTongThoiGianSuDungTatCaTaiKhoan @Thang , @Nam", param);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public DataTable NhanVienCuaThang(int thang, int nam)
        {
            try
            {
                object[] param = new object[] { thang, nam };
                return db.excuteQuery("SELECT * FROM fn_TimNhanVienCuaThang (@Thang , @Nam)", param);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public DataTable NhanVienCuaNam(int nam)
        {
            try
            {
                object[] param = new object[] { nam };
                return db.excuteQuery("SELECT * FROM fn_TimNhanVienCuaNam (@Nam)", param);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public DataTable KhachHangCuaThang(int thang, int nam)
        {
            try
            {
                object[] param = new object[] { thang, nam };
                return db.excuteQuery("SELECT * FROM fn_TimKhachHangCuaThang (@Thang , @Nam)", param);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public DataTable KhachHangCuaNam(int nam)
        {
            try
            {
                object[] param = new object[] { nam };
                return db.excuteQuery("SELECT * FROM fn_TimKhachHangCuaNam (@Nam)", param);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }
    }
}

     */
    private static DoanhThuDAO instance;
    private Connection connection;

    private DoanhThuDAO() {
        connection = DatabaseConnection.getInstance();
    }

    public static DoanhThuDAO getInstance() {
        if (instance == null) {
            instance = new DoanhThuDAO();
        }
        return instance;
    }

    public List<MayTinh> getDoanhThu12ThangGanNhat() {
        List<MayTinh> list = new ArrayList<>();
        String sql = "SELECT * FROM func_TinhDoanhThu12ThangGanNhat()";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                list.add(extractMayTinhFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getTongDoanhThuThang(int thang, int nam) {
        String sql = "SELECT dbo.func_TinhTongDoanhThuThang (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, thang);
            preparedStatement.setInt(2, nam);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getDoanhThuThangNay() {
        String sql = "EXEC proc_TinhDoanhThuThangNay";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Helper method to safely extract data from result set
    private MayTinh extractMayTinhFromResultSet(ResultSet rs) throws SQLException {
        String soMay = "";
        String trangThai = "";
        Date ngayLapDat = null;
        int maLoaiMay = 0;
        double thoiGianSD = 0;

        // Try to get each column, ignoring errors if column doesn't exist
        try {
            soMay = rs.getString("SoMay");
        } catch (SQLException e) {
            // Try alternative column names
            try {
                soMay = rs.getString("MaNV");
            } catch (SQLException e2) {
                try {
                    soMay = rs.getString("TenDangNhap");
                } catch (SQLException e3) {
                    try {
                        soMay = rs.getString(1); // Try first column
                    } catch (SQLException e4) {
                        soMay = "Unknown";
                    }
                }
            }
        }

        try {
            trangThai = rs.getString("TrangThai");
        } catch (SQLException e) {
            // Try alternative column names
            try {
                trangThai = rs.getString("HoTen");
            } catch (SQLException e2) {
                try {
                    trangThai = rs.getString(2); // Try second column
                } catch (SQLException e3) {
                    trangThai = "Unknown";
                }
            }
        }

        try {
            ngayLapDat = rs.getDate("NgayLapDat");
        } catch (SQLException e) {
            // Use current date if not available
            ngayLapDat = new Date(System.currentTimeMillis());
        }

        try {
            maLoaiMay = rs.getInt("MaLoaiMay");
        } catch (SQLException e) {
            // Try alternative column names
            try {
                maLoaiMay = rs.getInt("TongThoiGian_Gio");
            } catch (SQLException e2) {
                try {
                    maLoaiMay = rs.getInt(3); // Try third column
                } catch (SQLException e3) {
                    maLoaiMay = 0;
                }
            }
        }

        try {
            thoiGianSD = rs.getDouble("ThoiGianSD");
        } catch (SQLException e) {
            // Try alternative column names
            try {
                thoiGianSD = rs.getDouble("DoanhThu");
            } catch (SQLException e2) {
                try {
                    thoiGianSD = rs.getDouble("ChiTieu");
                } catch (SQLException e3) {
                    try {
                        thoiGianSD = rs.getDouble("TongTienNap");
                    } catch (SQLException e4) {
                        try {
                            thoiGianSD = rs.getDouble(4); // Try fourth column
                        } catch (SQLException e5) {
                            thoiGianSD = 0;
                        }
                    }
                }
            }
        }

        return new MayTinh(soMay, trangThai, ngayLapDat, maLoaiMay, thoiGianSD);
    }

    public List<MayTinh> getDoanhThuNhanVienTheoThang(int thang, int nam) {
        List<MayTinh> list = new ArrayList<>();
        String sql = "SELECT * FROM fn_TinhDoanhThuNhanVienTheoThang (?, ?) ORDER BY DoanhThu DESC";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, thang);
            preparedStatement.setInt(2, nam);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(extractMayTinhFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<MayTinh> getTongTGSDVaDTKH(int thang, int nam) {
        List<MayTinh> list = new ArrayList<>();
        String sql = "EXEC sp_TinhTongThoiGianSuDungTatCaTaiKhoan ?, ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, thang);
            preparedStatement.setInt(2, nam);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(extractMayTinhFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<MayTinh> getNhanVienCuaThang(int thang, int nam) {
        List<MayTinh> list = new ArrayList<>();
        String sql = "SELECT * FROM fn_TimNhanVienCuaThang (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, thang);
            preparedStatement.setInt(2, nam);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(extractMayTinhFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<MayTinh> getNhanVienCuaNam(int nam) {
        List<MayTinh> list = new ArrayList<>();
        String sql = "SELECT * FROM fn_TimNhanVienCuaNam (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, nam);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(extractMayTinhFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<MayTinh> getKhachHangCuaThang(int thang, int nam) {
        List<MayTinh> list = new ArrayList<>();
        String sql = "SELECT * FROM fn_TimKhachHangCuaThang (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, thang);
            preparedStatement.setInt(2, nam);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(extractMayTinhFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<MayTinh> getKhachHangCuaNam(int nam) {
        List<MayTinh> list = new ArrayList<>();
        String sql = "SELECT * FROM fn_TimKhachHangCuaNam (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, nam);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(extractMayTinhFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
