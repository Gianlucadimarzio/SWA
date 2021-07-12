-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Lug 12, 2021 alle 17:15
-- Versione del server: 10.4.20-MariaDB
-- Versione PHP: 8.0.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `collectorsite`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `autore`
--

CREATE TABLE `autore` (
  `id` int(11) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `cognome` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `autore`
--

INSERT INTO `autore` (`id`, `nome`, `cognome`) VALUES
(1, 'Manuel', 'Pozzoli');

-- --------------------------------------------------------

--
-- Struttura della tabella `collezione`
--

CREATE TABLE `collezione` (
  `id` int(11) NOT NULL,
  `id_utente` int(11) NOT NULL,
  `titolo` varchar(255) NOT NULL,
  `privacy` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `collezione`
--

INSERT INTO `collezione` (`id`, `id_utente`, `titolo`, `privacy`) VALUES
(1, 1, 'Summer 2k21', 'Pubblica'),
(2, 1, 'Primavera 2k21', 'Pubblica');

-- --------------------------------------------------------

--
-- Struttura della tabella `collezione_condivisa`
--

CREATE TABLE `collezione_condivisa` (
  `id` int(11) NOT NULL,
  `id_collezione` int(11) NOT NULL,
  `id_utente` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `dischi_collezione`
--

CREATE TABLE `dischi_collezione` (
  `id` int(11) NOT NULL,
  `id_collezione` int(11) NOT NULL,
  `id_disco` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `dischi_collezione`
--

INSERT INTO `dischi_collezione` (`id`, `id_collezione`, `id_disco`) VALUES
(1, 1, 1),
(2, 2, 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `disco`
--

CREATE TABLE `disco` (
  `id` int(11) NOT NULL,
  `id_autore` int(11) NOT NULL,
  `titolo` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `disco`
--

INSERT INTO `disco` (`id`, `id_autore`, `titolo`) VALUES
(1, 1, 'Disco Pernacchie');

-- --------------------------------------------------------

--
-- Struttura della tabella `tracce_disco`
--

CREATE TABLE `tracce_disco` (
  `id` int(11) NOT NULL,
  `id_disco` int(11) NOT NULL,
  `id_traccia` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `tracce_disco`
--

INSERT INTO `tracce_disco` (`id`, `id_disco`, `id_traccia`) VALUES
(1, 1, 1),
(2, 1, 2);

-- --------------------------------------------------------

--
-- Struttura della tabella `traccia`
--

CREATE TABLE `traccia` (
  `id` int(11) NOT NULL,
  `titolo` varchar(255) NOT NULL,
  `durata` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `traccia`
--

INSERT INTO `traccia` (`id`, `titolo`, `durata`) VALUES
(1, 'Traccia 1', 120),
(2, 'Traccia 2', 180);

-- --------------------------------------------------------

--
-- Struttura della tabella `utente`
--

CREATE TABLE `utente` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `utente`
--

INSERT INTO `utente` (`id`, `username`, `password`) VALUES
(1, 'Mario', '123'),
(2, 'Giovanni', '123');

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `autore`
--
ALTER TABLE `autore`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `collezione`
--
ALTER TABLE `collezione`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_utente` (`id_utente`);

--
-- Indici per le tabelle `collezione_condivisa`
--
ALTER TABLE `collezione_condivisa`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_collezione` (`id_collezione`),
  ADD KEY `id_utente` (`id_utente`);

--
-- Indici per le tabelle `dischi_collezione`
--
ALTER TABLE `dischi_collezione`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_collezione` (`id_collezione`),
  ADD KEY `id_disco` (`id_disco`);

--
-- Indici per le tabelle `disco`
--
ALTER TABLE `disco`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_autore` (`id_autore`);

--
-- Indici per le tabelle `tracce_disco`
--
ALTER TABLE `tracce_disco`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_disco` (`id_disco`),
  ADD KEY `id_traccia` (`id_traccia`);

--
-- Indici per le tabelle `traccia`
--
ALTER TABLE `traccia`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `utente`
--
ALTER TABLE `utente`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `autore`
--
ALTER TABLE `autore`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT per la tabella `collezione`
--
ALTER TABLE `collezione`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT per la tabella `collezione_condivisa`
--
ALTER TABLE `collezione_condivisa`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `dischi_collezione`
--
ALTER TABLE `dischi_collezione`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT per la tabella `disco`
--
ALTER TABLE `disco`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT per la tabella `tracce_disco`
--
ALTER TABLE `tracce_disco`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT per la tabella `traccia`
--
ALTER TABLE `traccia`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT per la tabella `utente`
--
ALTER TABLE `utente`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `collezione`
--
ALTER TABLE `collezione`
  ADD CONSTRAINT `collezione_ibfk_2` FOREIGN KEY (`id_utente`) REFERENCES `utente` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `collezione_condivisa`
--
ALTER TABLE `collezione_condivisa`
  ADD CONSTRAINT `collezione_condivisa_ibfk_1` FOREIGN KEY (`id_collezione`) REFERENCES `collezione` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `collezione_condivisa_ibfk_2` FOREIGN KEY (`id_utente`) REFERENCES `utente` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `dischi_collezione`
--
ALTER TABLE `dischi_collezione`
  ADD CONSTRAINT `dischi_collezione_ibfk_1` FOREIGN KEY (`id_collezione`) REFERENCES `collezione` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `dischi_collezione_ibfk_2` FOREIGN KEY (`id_disco`) REFERENCES `disco` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `disco`
--
ALTER TABLE `disco`
  ADD CONSTRAINT `disco_ibfk_1` FOREIGN KEY (`id_autore`) REFERENCES `autore` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `tracce_disco`
--
ALTER TABLE `tracce_disco`
  ADD CONSTRAINT `tracce_disco_ibfk_1` FOREIGN KEY (`id_disco`) REFERENCES `disco` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tracce_disco_ibfk_2` FOREIGN KEY (`id_traccia`) REFERENCES `traccia` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
