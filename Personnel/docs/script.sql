CREATE TABLE `employe` (
  `idEmploye` int NOT NULL,
  `nom` varchar(255),
  `prenom` varchar(255),
  `mail` varchar(255),
  `password` varchar(255),
  `dateArrivee` date DEFAULT NULL,
  `dateDepart` date DEFAULT NULL,
  `idLigue` int DEFAULT NULL
);


CREATE TABLE `ligue` (
  `idLigue` int NOT NULL,
  `nom` varchar(255),
  `idEmploye` int DEFAULT NULL
);

ALTER TABLE `employe`
  ADD PRIMARY KEY (`idEmploye`),
  ADD KEY `employe_ibfk_1` (`idLigue`);

--
-- Index pour la table `ligue`
--
ALTER TABLE `ligue`
  ADD PRIMARY KEY (`idLigue`),
  ADD KEY `ligue_ibfk_1` (`idEmploye`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `employe`
--
ALTER TABLE `employe`
  MODIFY `idEmploye` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=144;

--
-- AUTO_INCREMENT pour la table `ligue`
--
ALTER TABLE `ligue`
  MODIFY `idLigue` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `employe`
--
ALTER TABLE `employe`
  ADD CONSTRAINT `employe_ibfk_1` FOREIGN KEY (`idLigue`) REFERENCES `ligue` (`idLigue`);

--
-- Contraintes pour la table `ligue`
--
ALTER TABLE `ligue`
  ADD CONSTRAINT `ligue_ibfk_1` FOREIGN KEY (`idEmploye`) REFERENCES `employe` (`idEmploye`);