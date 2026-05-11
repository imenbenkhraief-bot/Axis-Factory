-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : lun. 11 mai 2026 à 18:18
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `axis_db`
--

-- --------------------------------------------------------

--
-- Structure de la table `machines`
--

CREATE TABLE `machines` (
  `id` varchar(10) NOT NULL,
  `name` varchar(50) NOT NULL,
  `type` varchar(50) NOT NULL,
  `status` varchar(20) DEFAULT 'READY',
  `parts` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `machines`
--

INSERT INTO `machines` (`id`, `name`, `type`, `status`, `parts`) VALUES
('7', 'Welder', 'WeldingRobot', 'READY', 24),
('M-001', 'Titan Welder', 'WeldingRobot', 'READY', 96),
('M-002', 'Vulcan ArcWelder', 'WeldingRobot', 'READY', 96),
('M-003', 'OmniMill 5-Axis', 'CNCMachine', 'READY', 120),
('M-004', 'ChromaCoat 500', 'PaintingRobot', 'READY', 40),
('M-005', 'HeavyLoad Crawler', 'ConveyorBelt', 'READY', 160),
('M-006', 'Nexus Assembler', 'AssemblyRobot', 'READY', 64);

-- --------------------------------------------------------

--
-- Structure de la table `workers`
--

CREATE TABLE `workers` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `role` varchar(50) NOT NULL,
  `extra_info` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `workers`
--

INSERT INTO `workers` (`id`, `name`, `role`, `extra_info`) VALUES
(1, 'Mohsen', 'OPERATOR', 'Line A'),
(2, 'Salah', 'TECHNICIAN', 'Maintenance'),
(3, 'Mounira', 'SUPERVISOR', 'DEP B'),
(4, 'Mohamed', 'TECHNICIAN', 'Maintenance'),
(5, 'Amor', 'OPERATOR', 'Line D'),
(6, 'Halima', 'SUPERVISOR', 'DEP A'),
(7, 'Hamza', 'TECHNICIAN', 'Maintenance'),
(8, 'Lasaad', 'OPERATOR', 'Line B'),
(9, 'Abdallah', 'TECHNICIAN', 'Maintenance'),
(10, 'Lotfi', 'OPERATOR', 'Dep A'),
(11, 'imen', 'OPERATOR', 'Line C');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `machines`
--
ALTER TABLE `machines`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `workers`
--
ALTER TABLE `workers`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `workers`
--
ALTER TABLE `workers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
