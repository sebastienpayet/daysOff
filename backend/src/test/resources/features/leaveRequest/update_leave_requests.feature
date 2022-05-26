#language: fr

Fonctionnalité: : modifier une demande de congés

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
      | prenom   | nom      | role          | services     | avecOuSansEnfantMineur | email             |
      | Elise    | Moualala | ADMINISTRATOR | MANAGEMENT   | avec                   | elise@test.com    |
      | Arthur   | Rololo   | USER          | LOCAL_POLICE | sans                   | arthur@test.com   |
      | Manager1 | Manager1 | TEAM_MANAGER  | LOCAL_POLICE | sans                   | manager@test.com  |
      | user1    | user1    | USER          | SERVICE1     | sans                   | service1@test.com |
      | user2    | user2    | USER          | SERVICE2     | sans                   | service2@test.com |
      | user3    | user3    | USER          | SERVICE3     | sans                   | service3@test.com |
      | user4    | user4    | USER          | SERVICE4     | sans                   | service4@test.com |


  Plan du scénario: en tant qu'administrateur, je peux modifier des demandes de congés à l'état de brouillon
    Etant donné que je suis connecté en tant qu'ADMINISTRATEUR
    Quand je crée une demande congés pour "<nom>" "<prenom>" du "<dateDebut1>" à "<dateFin1>" de type "<typeConge1>"
    Et que je soumet la demande congés pour "<nom>" "<prenom>" du "<dateDebut1>" à "<dateFin1>" de type "<typeConge1>" pour validation
    Et que je modifie une demande congés pour "<nom>" "<prenom>" de "<dateDebut1>" à "<dateFin1>" de type "<typeConge1>" vers "<dateDebut2>" à "<dateFin2>" de type "<typeConge2>"
    Alors une erreur me signalant que je ne peux modifier que les demandes à l'état de brouillon est produite
    Exemples:
      | prenom | nom    | dateDebut1 | dateFin1   | typeConge1 | dateDebut2 | dateFin2   | typeConge2             |
      | Arthur | Rololo | 05-01-2020 | 10-01-2020 | ANNUAL     | 01-01-2020 | 01-07-2020 | RECOVERY               |
      | Arthur | Rololo | 05-01-2020 | 10-01-2020 | ANNUAL     | 07-01-2020 | 20-07-2020 | SPECIAL                |
      | Arthur | Rololo | 05-01-2020 | 10-01-2020 | ANNUAL     | 01-01-2020 | 20-07-2020 | WORKING_TIME_REDUCTION |
      | Arthur | Rololo | 05-01-2020 | 10-01-2020 | ANNUAL     | 06-01-2020 | 09-07-2020 | TIME_SAVING_ACCOUNT    |


  Plan du scénario: en tant qu'administrateur, je ne peux pas modifier des demandes qui ne sont pas a l'état de brouillon
    Etant donné que je suis connecté en tant qu'ADMINISTRATEUR
    Quand je crée une demande congés pour "<nom>" "<prenom>" du "<dateDebut1>" à "<dateFin1>" de type "<typeConge1>"
    Et que je modifie une demande congés pour "<nom>" "<prenom>" de "<dateDebut1>" à "<dateFin1>" de type "<typeConge1>" vers "<dateDebut2>" à "<dateFin2>" de type "<typeConge2>"
    Alors la demande congés pour "<nom>" "<prenom>" du "<dateDebut2>" à "<dateFin2>" de type "<typeConge2>" existe
    Et aucune erreur n'a été produite

    Exemples:
      | prenom | nom    | dateDebut1 | dateFin1   | typeConge1 | dateDebut2 | dateFin2   | typeConge2             |
      | Arthur | Rololo | 05-01-2020 | 10-01-2020 | ANNUAL     | 01-01-2020 | 01-07-2020 | RECOVERY               |
      | Arthur | Rololo | 05-01-2020 | 10-01-2020 | ANNUAL     | 07-01-2020 | 20-07-2020 | SPECIAL                |
      | Arthur | Rololo | 05-01-2020 | 10-01-2020 | ANNUAL     | 01-01-2020 | 20-07-2020 | WORKING_TIME_REDUCTION |
      | Arthur | Rololo | 05-01-2020 | 10-01-2020 | ANNUAL     | 06-01-2020 | 09-07-2020 | TIME_SAVING_ACCOUNT    |

  Plan du scénario: en tant qu'administrateur, je ne peux pas modifier des demandes avec des données incohérentes
    Etant donné que je suis connecté en tant qu'ADMINISTRATEUR
    Quand je crée une demande congés pour "<nom>" "<prenom>" du "<dateDebut1>" à "<dateFin1>" de type "<typeConge1>"
    Et que je soumet la demande congés pour "<nom>" "<prenom>" du "<dateDebut1>" à "<dateFin1>" de type "<typeConge1>" pour validation
    Et que je modifie une demande congés pour "<nom>" "<prenom>" de "<dateDebut1>" à "<dateFin1>" de type "<typeConge1>" vers "<dateDebut2>" à "<dateFin2>" de type "<typeConge2>"
    Alors une erreur me signalant que je ne peux modifier que les demandes à l'état de brouillon est produite

    Exemples:
      | prenom | nom    | dateDebut1 | dateFin1   | typeConge1 | dateDebut2 | dateFin2   | typeConge2             |
      | Arthur | Rololo | 05-01-2020 | 10-01-2020 | ANNUAL     | 01-01-2020 | 01-07-2020 | RECOVERY               |
      | Arthur | Rololo | 05-01-2020 | 10-01-2020 | ANNUAL     | 07-01-2020 | 20-07-2020 | SPECIAL                |
      | Arthur | Rololo | 05-01-2020 | 10-01-2020 | ANNUAL     | 01-01-2020 | 20-07-2020 | WORKING_TIME_REDUCTION |
      | Arthur | Rololo | 05-01-2020 | 10-01-2020 | ANNUAL     | 06-01-2020 | 09-07-2020 | TIME_SAVING_ACCOUNT    |

  Plan du scénario: en tant que manager, je peux modifier des demandes qui m'appartienne à l'état de brouillon
    Etant donné que je suis connecté en tant que MANAGER
    Quand je crée une demande congés pour "<nom>" "<prenom>" du "<dateDebut1>" à "<dateFin1>" de type "<typeConge1>"
    Et que je modifie une demande congés pour "<nom>" "<prenom>" de "<dateDebut1>" à "<dateFin1>" de type "<typeConge1>" vers "<dateDebut2>" à "<dateFin2>" de type "<typeConge2>"
    Alors la demande congés pour "<nom>" "<prenom>" du "<dateDebut2>" à "<dateFin2>" de type "<typeConge2>" existe
    Et aucune erreur n'a été produite

    Exemples:
      | prenom   | nom      | dateDebut1 | dateFin1   | typeConge1 | dateDebut2 | dateFin2   | typeConge2             |
      | Manager1 | Manager1 | 05-01-2020 | 10-01-2020 | ANNUAL     | 01-01-2020 | 01-07-2020 | RECOVERY               |
      | Arthur   | Rololo   | 05-01-2020 | 10-01-2020 | ANNUAL     | 07-01-2020 | 20-07-2020 | SPECIAL                |
      | Manager1 | Manager1 | 05-01-2020 | 10-01-2020 | ANNUAL     | 01-01-2020 | 20-07-2020 | WORKING_TIME_REDUCTION |
      | Arthur   | Rololo   | 05-01-2020 | 10-01-2020 | ANNUAL     | 06-01-2020 | 09-07-2020 | TIME_SAVING_ACCOUNT    |

  Plan du scénario: en tant que manager, je ne peux pas modifier des demandes de mon équipe et qui ne sont pas a l'état de brouillon
    Etant donné que je suis connecté en tant que MANAGER
    Quand je crée une demande congés pour "<nom>" "<prenom>" du "<dateDebut1>" à "<dateFin1>" de type "<typeConge1>"
    Et que je soumet la demande congés pour "<nom>" "<prenom>" du "<dateDebut1>" à "<dateFin1>" de type "<typeConge1>" pour validation
    Et que je modifie une demande congés pour "<nom>" "<prenom>" de "<dateDebut1>" à "<dateFin1>" de type "<typeConge1>" vers "<dateDebut2>" à "<dateFin2>" de type "<typeConge2>"
    Alors une erreur me signalant que je ne peux modifier que les demandes à l'état de brouillon est produite

    Exemples:
      | prenom   | nom      | dateDebut1 | dateFin1   | typeConge1 | dateDebut2 | dateFin2   | typeConge2             |
      | Manager1 | Manager1 | 05-01-2020 | 10-01-2020 | ANNUAL     | 01-01-2020 | 01-07-2020 | RECOVERY               |
      | Arthur   | Rololo   | 05-01-2020 | 10-01-2020 | ANNUAL     | 07-01-2020 | 20-07-2020 | SPECIAL                |
      | Manager1 | Manager1 | 05-01-2020 | 10-01-2020 | ANNUAL     | 01-01-2020 | 20-07-2020 | WORKING_TIME_REDUCTION |
      | Arthur   | Rololo   | 05-01-2020 | 10-01-2020 | ANNUAL     | 06-01-2020 | 09-07-2020 | TIME_SAVING_ACCOUNT    |

  Plan du scénario: en tant que manager, je ne peux pas modifier des brouillons qui ne sont pas de mes équipes
    Etant donné que je suis connecté en tant que MANAGER
    Et qu'il existe une demande congés pour "<nom>" "<prenom>" du "<dateDebut1>" à "<dateFin1>" de type "<typeConge1>"
    Et que je modifie une demande congés pour "<nom>" "<prenom>" de "<dateDebut1>" à "<dateFin1>" de type "<typeConge1>" vers "<dateDebut2>" à "<dateFin2>" de type "<typeConge2>"
    Alors une erreur signalant que je ne peux modifier que les demandes de mes équipe est produite

    Exemples:
      | prenom | nom   | dateDebut1 | dateFin1   | typeConge1 | dateDebut2 | dateFin2   | typeConge2             |
      | user1  | user1 | 05-01-2020 | 10-01-2020 | ANNUAL     | 01-01-2020 | 01-07-2020 | RECOVERY               |
      | user2  | user2 | 05-01-2020 | 10-01-2020 | ANNUAL     | 07-01-2020 | 20-07-2020 | SPECIAL                |
      | user3  | user3 | 05-01-2020 | 10-01-2020 | ANNUAL     | 01-01-2020 | 20-07-2020 | WORKING_TIME_REDUCTION |
      | user4  | user4 | 05-01-2020 | 10-01-2020 | ANNUAL     | 06-01-2020 | 09-07-2020 | TIME_SAVING_ACCOUNT    |

  Plan du scénario: en tant qu'utilisateur, je peux modifier des mes demandes à l'état de brouillon
    Etant donné que je suis connecté en tant qu'USAGER
    Et qu'il existe une demande congés pour "<nom>" "<prenom>" du "<dateDebut1>" à "<dateFin1>" de type "<typeConge1>"
    Et que je modifie une demande congés pour "<nom>" "<prenom>" de "<dateDebut1>" à "<dateFin1>" de type "<typeConge1>" vers "<dateDebut2>" à "<dateFin2>" de type "<typeConge2>"
    Alors la demande congés pour "<nom>" "<prenom>" du "<dateDebut2>" à "<dateFin2>" de type "<typeConge2>" existe
    Et aucune erreur n'a été produite

    Exemples:
      | prenom | nom    | dateDebut1 | dateFin1   | typeConge1 | dateDebut2 | dateFin2   | typeConge2             |
      | Arthur | Rololo | 05-01-2020 | 10-01-2020 | ANNUAL     | 01-01-2020 | 01-07-2020 | RECOVERY               |
      | Arthur | Rololo | 05-01-2020 | 10-01-2020 | ANNUAL     | 07-01-2020 | 20-07-2020 | SPECIAL                |
      | Arthur | Rololo | 05-01-2020 | 10-01-2020 | ANNUAL     | 01-01-2020 | 20-07-2020 | WORKING_TIME_REDUCTION |
      | Arthur | Rololo | 05-01-2020 | 10-01-2020 | ANNUAL     | 06-01-2020 | 09-07-2020 | TIME_SAVING_ACCOUNT    |

  Plan du scénario: en tant qu'utilisateur, je ne peux pas modifier mes demandes qui ne soit pas a l'état de brouillon
    Etant donné que je suis connecté en tant qu'USAGER
    Quand je crée une demande congés pour "<nom>" "<prenom>" du "<dateDebut1>" à "<dateFin1>" de type "<typeConge1>"
    Et que je soumet la demande congés pour "<nom>" "<prenom>" du "<dateDebut1>" à "<dateFin1>" de type "<typeConge1>" pour validation
    Et que je modifie une demande congés pour "<nom>" "<prenom>" de "<dateDebut1>" à "<dateFin1>" de type "<typeConge1>" vers "<dateDebut2>" à "<dateFin2>" de type "<typeConge2>"
    Alors une erreur me signalant que je ne peux modifier que les demandes à l'état de brouillon est produite

    Exemples:
      | prenom | nom    | dateDebut1 | dateFin1   | typeConge1 | dateDebut2 | dateFin2   | typeConge2             |
      | Arthur | Rololo | 05-01-2020 | 10-01-2020 | ANNUAL     | 01-01-2020 | 01-07-2020 | RECOVERY               |
      | Arthur | Rololo | 05-01-2020 | 10-01-2020 | ANNUAL     | 07-01-2020 | 20-07-2020 | SPECIAL                |
      | Arthur | Rololo | 05-01-2020 | 10-01-2020 | ANNUAL     | 01-01-2020 | 20-07-2020 | WORKING_TIME_REDUCTION |
      | Arthur | Rololo | 05-01-2020 | 10-01-2020 | ANNUAL     | 06-01-2020 | 09-07-2020 | TIME_SAVING_ACCOUNT    |

  Plan du scénario: en tant qu'utilisateur, je ne peux pas modifier des demandes d'autres personnes
    Etant donné que je suis connecté en tant qu'USAGER
    Et qu'il existe une demande congés pour "<nom>" "<prenom>" du "<dateDebut1>" à "<dateFin1>" de type "<typeConge1>"
    Et que je modifie une demande congés pour "<nom>" "<prenom>" de "<dateDebut1>" à "<dateFin1>" de type "<typeConge1>" vers "<dateDebut2>" à "<dateFin2>" de type "<typeConge2>"
    Alors une erreur signalant que je ne peux modifier que mes demandes est produite

    Exemples:
      | prenom | nom   | dateDebut1 | dateFin1   | typeConge1 | dateDebut2 | dateFin2   | typeConge2             |
      | user1  | user1 | 05-01-2020 | 10-01-2020 | ANNUAL     | 01-01-2020 | 01-07-2020 | RECOVERY               |
      | user2  | user2 | 05-01-2020 | 10-01-2020 | ANNUAL     | 07-01-2020 | 20-07-2020 | SPECIAL                |
      | user3  | user3 | 05-01-2020 | 10-01-2020 | ANNUAL     | 01-01-2020 | 20-07-2020 | WORKING_TIME_REDUCTION |
      | user4  | user4 | 05-01-2020 | 10-01-2020 | ANNUAL     | 06-01-2020 | 09-07-2020 | TIME_SAVING_ACCOUNT    |
