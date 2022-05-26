#language: fr

Fonctionnalité: : créer une demande de congés

  Contexte:
    Etant donné que des equipes existent
      | nom          |
      | MANAGEMENT   |
      | LOCAL_POLICE |
      | SERVICE1     |
      | SERVICE2     |
      | SERVICE3     |
      | SERVICE4     |

    Etant donné que des utilisateurs existent
      | prenom   | nom      | role          | services              | avecOuSansEnfantMineur | email             |
      | Elise    | Moualala | ADMINISTRATOR | MANAGEMENT            | avec                   | elise@test.com    |
      | Arthur   | Rololo   | USER          | LOCAL_POLICE          | sans                   | arthur@test.com   |
      | Manager1 | manager1 | TEAM_MANAGER  | LOCAL_POLICE,SERVICE1 | sans                   | manager@test.com  |
      | user1    | user1    | USER          | SERVICE1              | sans                   | service1@test.com |
      | user2    | user2    | USER          | SERVICE2              | sans                   | service2@test.com |
      | user3    | user3    | USER          | SERVICE3              | sans                   | service3@test.com |
      | user4    | user4    | USER          | SERVICE4              | sans                   | service4@test.com |

  Plan du scénario: en tant qu'administrateur, je peux créer de nouvelles demandes de congés
    Etant donné que je suis connecté en tant qu'ADMINISTRATEUR
    Quand je crée une demande congés pour "<nom>" "<prenom>" du "<dateDebut>" à "<dateFin>" de type "<typeConge>"
    Alors la demande congés pour "<nom>" "<prenom>" du "<dateDebut>" à "<dateFin>" de type "<typeConge>" existe pour une durée de "<dureeAttendue>"
    Et aucune erreur n'a été produite
    Exemples:
      | prenom | nom    | dateDebut  | dateFin    | typeConge              | dureeAttendue |
      | Arthur | Rololo | 01-01-2021 | 15-01-2021 | ANNUAL                 | 10            |
      | Arthur | Rololo | 01-06-2021 | 20-06-2021 | RECOVERY               | 14            |
      | Arthur | Rololo | 02-02-2021 | 18-02-2021 | SPECIAL                | 13            |
      | Arthur | Rololo | 01-02-2021 | 23-02-2021 | WORKING_TIME_REDUCTION | 17            |
      | Arthur | Rololo | 03-04-2021 | 30-04-2021 | TIME_SAVING_ACCOUNT    | 19            |

  Plan du scénario: en tant qu'administrateur, si je créer des demandes de congés, elle tiennent compte des jours fériés
    Etant donné que je suis connecté en tant qu'ADMINISTRATEUR
    Quand je crée une demande congés pour "<nom>" "<prenom>" du "<dateDebut>" à "<dateFin>" de type "<typeConge>"
    Alors la demande congés pour "<nom>" "<prenom>" du "<dateDebut>" à "<dateFin>" de type "<typeConge>" existe pour une durée de "<dureeAttendue>"
    Et aucune erreur n'a été produite
    Exemples:
      | prenom | nom    | dateDebut  | dateFin    | typeConge | dureeAttendue |
      | Arthur | Rololo | 01-05-2021 | 01-05-2021 | ANNUAL    | 0             |
      | Arthur | Rololo | 01-05-2021 | 05-05-2021 | ANNUAL    | 3             |
      | Arthur | Rololo | 10-05-2021 | 16-05-2021 | ANNUAL    | 4             |
      | Arthur | Rololo | 24-05-2021 | 30-05-2021 | ANNUAL    | 4             |

  Plan du scénario: en tant qu'administrateur, je peux créer des demandes qui sont en brouillon et qui se chevauchent
    Etant donné que je suis connecté en tant qu'ADMINISTRATEUR
    Quand je crée une demande congés pour "<nom>" "<prenom>" du "<dateDebut1>" à "<dateFin1>" de type "<typeConge1>"
    Et que je crée une demande congés pour "<nom>" "<prenom>" du "<dateDebut2>" à "<dateFin2>" de type "<typeConge2>"
    Alors la demande congés pour "<nom>" "<prenom>" du "<dateDebut1>" à "<dateFin1>" de type "<typeConge1>" existe
    Et la demande congés pour "<nom>" "<prenom>" du "<dateDebut2>" à "<dateFin2>" de type "<typeConge2>" existe
    Et aucune erreur n'a été produite

    Exemples:
      | prenom | nom    | dateDebut1 | dateFin1   | typeConge1 | dateDebut2 | dateFin2   | typeConge2             |
      | Arthur | Rololo | 05-01-2020 | 10-01-2020 | ANNUAL     | 01-01-2020 | 01-07-2020 | RECOVERY               |
      | Arthur | Rololo | 05-01-2020 | 10-01-2020 | ANNUAL     | 07-01-2020 | 20-07-2020 | SPECIAL                |
      | Arthur | Rololo | 05-01-2020 | 10-01-2020 | ANNUAL     | 01-01-2020 | 20-07-2020 | WORKING_TIME_REDUCTION |
      | Arthur | Rololo | 05-01-2020 | 10-01-2020 | ANNUAL     | 06-01-2020 | 09-07-2020 | TIME_SAVING_ACCOUNT    |

  Plan du scénario: en tant que manager, je peux créer des demandes pour les membres de mes équipes
    Etant donné que je suis connecté en tant que "manager1" "Manager1"
    Quand je crée une demande congés pour "<nom>" "<prenom>" du "<dateDebut1>" à "<dateFin1>" de type "<typeConge1>"
    Alors la demande congés pour "<nom>" "<prenom>" du "<dateDebut1>" à "<dateFin1>" de type "<typeConge1>" existe
    Et aucune erreur n'a été produite

    Exemples:
      | prenom | nom    | dateDebut1 | dateFin1   | typeConge1             |
      | Arthur | Rololo | 01-01-2020 | 15-01-2020 | ANNUAL                 |
      | user1  | user1  | 01-06-2020 | 20-06-2020 | RECOVERY               |
      | Arthur | Rololo | 01-02-2020 | 18-02-2020 | SPECIAL                |
      | user1  | user1  | 01-02-2020 | 23-02-2020 | WORKING_TIME_REDUCTION |
      | Arthur | Rololo | 03-04-2020 | 31-04-2020 | TIME_SAVING_ACCOUNT    |

  Plan du scénario: en tant que manager, je ne peux pas créer des demandes hors de mes équipes
    Etant donné que je suis connecté en tant que "manager1" "Manager1"
    Quand je crée une demande congés pour "<nom>" "<prenom>" du "<dateDebut1>" à "<dateFin1>" de type "<typeConge1>"
    Alors une erreur signalant que je ne peux créer que des demandes dans mes équipes est levée

    Exemples:
      | prenom | nom      | dateDebut1 | dateFin1   | typeConge1             |
      | user2  | user2    | 01-01-2020 | 15-01-2020 | ANNUAL                 |
      | user3  | user3    | 01-06-2020 | 20-06-2020 | RECOVERY               |
      | user4  | user4    | 01-02-2020 | 18-02-2020 | SPECIAL                |
      | Elise  | Moualala | 01-02-2020 | 23-02-2020 | WORKING_TIME_REDUCTION |

  Plan du scénario: en tant qu'utilisateur, je ne peux pas créer des demandes pour d'autres personnes
    Etant donné que je suis connecté en tant que "Rololo" "Arthur"
    Quand je crée une demande congés pour "<nom>" "<prenom>" du "<dateDebut1>" à "<dateFin1>" de type "<typeConge1>"
    Alors une erreur signalant que je ne peux créer que des demandes pour moi est levée

    Exemples:
      | prenom | nom      | dateDebut1 | dateFin1   | typeConge1             |
      | user2  | user2    | 01-01-2020 | 15-01-2020 | ANNUAL                 |
      | user3  | user3    | 01-06-2020 | 20-06-2020 | RECOVERY               |
      | user4  | user4    | 01-02-2020 | 18-02-2020 | SPECIAL                |
      | Elise  | Moualala | 01-02-2020 | 23-02-2020 | WORKING_TIME_REDUCTION |

  Plan du scénario: en tant qu'utilisateur, je peux créer des demandes pour moi
    Etant donné que je suis connecté en tant que "Rololo" "Arthur"
    Quand je crée une demande congés pour "<nom>" "<prenom>" du "<dateDebut1>" à "<dateFin1>" de type "<typeConge1>"
    Alors la demande congés pour "<nom>" "<prenom>" du "<dateDebut1>" à "<dateFin1>" de type "<typeConge1>" existe
    Et aucune erreur n'a été produite

    Exemples:
      | prenom | nom    | dateDebut1 | dateFin1   | typeConge1             |
      | Arthur | Rololo | 01-01-2021 | 15-01-2021 | ANNUAL                 |
      | Arthur | Rololo | 01-06-2021 | 20-06-2021 | RECOVERY               |
      | Arthur | Rololo | 02-02-2021 | 18-02-2021 | SPECIAL                |
      | Arthur | Rololo | 01-02-2021 | 23-02-2021 | WORKING_TIME_REDUCTION |
      | Arthur | Rololo | 03-04-2021 | 30-04-2021 | TIME_SAVING_ACCOUNT    |

