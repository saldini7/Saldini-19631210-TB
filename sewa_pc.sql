-- phpMyAdmin SQL Dump
-- version 5.0.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 08, 2022 at 01:56 PM
-- Server version: 10.4.14-MariaDB
-- PHP Version: 7.2.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sewa_pc`
--

-- --------------------------------------------------------

--
-- Table structure for table `member`
--

CREATE TABLE `member` (
  `id_member` varchar(10) NOT NULL,
  `nama_lengkap` varchar(50) NOT NULL,
  `jenis_kelamin` enum('Laki-laki','Perempuan') NOT NULL,
  `alamat` varchar(100) NOT NULL,
  `telp` varchar(15) NOT NULL,
  `email` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `member`
--

INSERT INTO `member` (`id_member`, `nama_lengkap`, `jenis_kelamin`, `alamat`, `telp`, `email`) VALUES
('MB-1', 'Saldini', 'Perempuan', 'Banjarbaru', '086655445544', 'email@email.com'),
('MB-2', 'Muhammad Abduh', 'Laki-laki', 'Banjarmasin', '089999999999', 'email@email.com'),
('MB-3', 'Dodi Yansah', 'Laki-laki', 'Kotabaru', '088888888888', 'email@email.com'),
('MB-4', 'Candu Winata', 'Laki-laki', 'Martapura', '087654321000', 'email@email.com'),
('MB-5', 'Rani Sari', 'Perempuan', 'Banjarbaru', '0999999999999', 'email@email.com');

-- --------------------------------------------------------

--
-- Table structure for table `pc`
--

CREATE TABLE `pc` (
  `no_pc` varchar(10) NOT NULL,
  `merek` varchar(100) NOT NULL,
  `jenis` varchar(50) NOT NULL,
  `harga` int(11) NOT NULL,
  `stok` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `pc`
--

INSERT INTO `pc` (`no_pc`, `merek`, `jenis`, `harga`, `stok`) VALUES
('PC-1', 'Acer Aspire E5-471-3G5E', 'PC', 50000, 5),
('PC-2', 'PC dell Alienware Core i5', 'PC', 50000, 5),
('PC-3', 'PC AIO Lenovo 3 22ADA05', 'PC', 50000, 10),
('PC-4', 'PC AKK IN ONE Lenovo V130I INTEL', 'PC', 50000, 200),
('PC-5', 'ASUS CP3130 ID001D', 'PC', 50000, 10);

-- --------------------------------------------------------

--
-- Table structure for table `transaksi_sewa`
--

CREATE TABLE `transaksi_sewa` (
  `id_sewa` varchar(10) NOT NULL,
  `id_member` varchar(10) NOT NULL,
  `no_pc` varchar(10) NOT NULL,
  `jumlah_hari` int(11) NOT NULL,
  `biaya` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transaksi_sewa`
--

INSERT INTO `transaksi_sewa` (`id_sewa`, `id_member`, `no_pc`, `jumlah_hari`, `biaya`) VALUES
('TS-1', 'MB-2', 'PC-1', 2, 100000),
('TS-2', 'MB-3', 'PC-1', 3, 150000);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `member`
--
ALTER TABLE `member`
  ADD PRIMARY KEY (`id_member`);

--
-- Indexes for table `pc`
--
ALTER TABLE `pc`
  ADD PRIMARY KEY (`no_pc`);

--
-- Indexes for table `transaksi_sewa`
--
ALTER TABLE `transaksi_sewa`
  ADD PRIMARY KEY (`id_sewa`),
  ADD KEY `id_member` (`id_member`),
  ADD KEY `no_pc` (`no_pc`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `transaksi_sewa`
--
ALTER TABLE `transaksi_sewa`
  ADD CONSTRAINT `transaksi_sewa_ibfk_1` FOREIGN KEY (`id_member`) REFERENCES `member` (`id_member`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `transaksi_sewa_ibfk_2` FOREIGN KEY (`no_pc`) REFERENCES `pc` (`no_pc`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
