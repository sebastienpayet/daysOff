#language: fr

Fonctionnalité: : gérer le cycle de vie d'une demande de congés

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
      | Manager1 | manager1 | TEAM_MANAGER  | LOCAL_POLICE | sans                   | manager@test.com  |
      | user1    | user1    | USER          | SERVICE1     | sans                   | service1@test.com |
      | user2    | user2    | USER          | SERVICE2     | sans                   | service2@test.com |
      | user3    | user3    | USER          | SERVICE3     | sans                   | service3@test.com |
      | user4    | user4    | USER          | SERVICE4     | sans                   | service4@test.com |

  Plan du scénario: en tant qu'administrateur, je peux envoyer en validation de services des demandes de n'importe qui
    Etant donné que je suis connecté en tant qu'ADMINISTRATEUR
    Quand je crée une demande congés pour "<nom>" "<prenom>" du "<dateDebut1>" à "<dateFin1>" de type "<typeConge1>"
    Et que je soumet la demande congés pour "<nom>" "<prenom>" du "<dateDebut1>" à "<dateFin1>" de type "<typeConge1>" pour validation
    Alors la demande congés pour "<nom>" "<prenom>" du "<dateDebut1>" à "<dateFin1>" de type "<typeConge1>" existe dans un status "<status1>"
    Et aucune erreur n'a été produite

    Exemples:
      | prenom | nom    | dateDebut1 | dateFin1   | typeConge1             | status1   |
      | Arthur | Rololo | 01-01-2021 | 15-01-2021 | ANNUAL                 | SUBMITTED |
      | Arthur | Rololo | 01-06-2021 | 20-06-2021 | RECOVERY               | SUBMITTED |
      | Arthur | Rololo | 01-02-2021 | 18-02-2021 | SPECIAL                | SUBMITTED |
      | Arthur | Rololo | 01-02-2021 | 23-02-2021 | WORKING_TIME_REDUCTION | SUBMITTED |
      | Arthur | Rololo | 03-04-2021 | 31-04-2021 | TIME_SAVING_ACCOUNT    | SUBMITTED |

  Plan du scénario: en tant qu'administrateur, je peux envoyer en validation de service des demandes de n'importe qui
    Etant donné que je suis connecté en tant qu'ADMINISTRATEUR
    Quand je crée une demande congés pour "<nom>" "<prenom>" du "<dateDebut1>" à "<dateFin1>" de type "<typeConge1>"
    Et que je valide par le service la demande congés pour "<nom>" "<prenom>" du "<dateDebut1>" à "<dateFin1>" de type "<typeConge1>"
    Alors la demande congés pour "<nom>" "<prenom>" du "<dateDebut1>" à "<dateFin1>" de type "<typeConge1>" existe dans un status "<status1>"
    Et aucune erreur n'a été produite

    Exemples:
      | prenom | nom    | dateDebut1 | dateFin1   | typeConge1             | status1          |
      | Arthur | Rololo | 01-01-2021 | 15-01-2021 | ANNUAL                 | SERVICE_APPROVED |
      | Arthur | Rololo | 01-06-2021 | 20-06-2021 | RECOVERY               | SERVICE_APPROVED |
      | Arthur | Rololo | 01-02-2021 | 18-02-2021 | SPECIAL                | SERVICE_APPROVED |
      | Arthur | Rololo | 01-02-2021 | 23-02-2021 | WORKING_TIME_REDUCTION | SERVICE_APPROVED |
      | Arthur | Rololo | 03-04-2021 | 31-04-2021 | TIME_SAVING_ACCOUNT    | SERVICE_APPROVED |

  Plan du scénario: en tant qu'administrateur, je peux valider definitivement des demandes de n'importe qui
    Etant donné que je suis connecté en tant qu'ADMINISTRATEUR
    Quand je crée une demande congés pour "<nom>" "<prenom>" du "<dateDebut1>" à "<dateFin1>" de type "<typeConge1>"
    Et que je valide définitivement la demande congés pour "<nom>" "<prenom>" du "<dateDebut1>" à "<dateFin1>" de type "<typeConge1>"
    Alors la demande congés pour "<nom>" "<prenom>" du "<dateDebut1>" à "<dateFin1>" de type "<typeConge1>" existe dans un status "<status1> avec la répartition annual: "<annualBalance>", recovery: "<recoveryBalance>", special: "<specialBalance>", WTR: "<WTRBalance>", TSA: "<TSABalance>""
    Et les soldes de congés pour "<nom>" "<prenom>" du "<dateDebut1>" à "<dateFin1>" de type "<typeConge1>" existent (annual: "<annualBalance>", recovery: "<recoveryBalance>", special: "<specialBalance>", WTR: "<WTRBalance>", TSA: "<TSABalance>")
    Et aucune erreur n'a été produite

    Exemples:
      | prenom | nom    | dateDebut1 | dateFin1   | typeConge1             | status1             | annualBalance | recoveryBalance | specialBalance | WTRBalance | TSABalance |
      | Arthur | Rololo | 01-01-2021 | 15-01-2021 | ANNUAL                 | MANAGEMENT_APPROVED | 0             | 0               | 0              | 0          | 0          |
      | Arthur | Rololo | 01-06-2021 | 20-06-2021 | RECOVERY               | MANAGEMENT_APPROVED | 0             | 0               | 0              | 0          | 0          |
      | Arthur | Rololo | 01-02-2021 | 18-02-2021 | SPECIAL                | MANAGEMENT_APPROVED | 0             | 0               | 0              | 0          | 0          |
      | Arthur | Rololo | 01-02-2021 | 23-02-2021 | WORKING_TIME_REDUCTION | MANAGEMENT_APPROVED | 0             | 0               | 0              | 0          | 0          |
      | Arthur | Rololo | 03-04-2021 | 31-04-2021 | TIME_SAVING_ACCOUNT    | MANAGEMENT_APPROVED | 0             | 0               | 0              | 0          | 0          |

  Plan du scénario: en tant qu'administrateur, je peux refuser des demandes de n'importe qui
    Etant donné que je suis connecté en tant qu'ADMINISTRATEUR

    Exemples:
      | prenom | nom    | dateDebut  | dateFin    | typeConge              | dureeAttendue |
      | Arthur | Rololo | 01-01-2020 | 01-15-2020 | ANNUAL                 | 0             |
      | Arthur | Rololo | 01-06-2020 | 01-20-2020 | RECOVERY               | 0             |
      | Arthur | Rololo | 01-02-2020 | 02-18-2020 | SPECIAL                | 0             |
      | Arthur | Rololo | 01-02-2020 | 01-23-2020 | WORKING_TIME_REDUCTION | 0             |
      | Arthur | Rololo | 03-04-2020 | 31-04-2020 | TIME_SAVING_ACCOUNT    | 0             |

  Plan du scénario: en tant qu'administrateur, je peux archiver des demandes de n'importe qui
    Etant donné que je suis connecté en tant qu'ADMINISTRATEUR

    Exemples:
      | prenom | nom    | dateDebut  | dateFin    | typeConge              | dureeAttendue |
      | Arthur | Rololo | 01-01-2020 | 01-15-2020 | ANNUAL                 | 0             |
      | Arthur | Rololo | 01-06-2020 | 01-20-2020 | RECOVERY               | 0             |
      | Arthur | Rololo | 01-02-2020 | 02-18-2020 | SPECIAL                | 0             |
      | Arthur | Rololo | 01-02-2020 | 01-23-2020 | WORKING_TIME_REDUCTION | 0             |
      | Arthur | Rololo | 03-04-2020 | 31-04-2020 | TIME_SAVING_ACCOUNT    | 0             |


  Plan du scénario: en tant que manager, je ne peux pas archiver des demandes
    Etant donné que je suis connecté en tant que MANAGER

    Exemples:
      | prenom | nom    | dateDebut  | dateFin    | typeConge              | dureeAttendue |
      | Arthur | Rololo | 01-01-2020 | 01-15-2020 | ANNUAL                 | 0             |
      | Arthur | Rololo | 01-06-2020 | 01-20-2020 | RECOVERY               | 0             |
      | Arthur | Rololo | 01-02-2020 | 02-18-2020 | SPECIAL                | 0             |
      | Arthur | Rololo | 01-02-2020 | 01-23-2020 | WORKING_TIME_REDUCTION | 0             |
      | Arthur | Rololo | 03-04-2020 | 31-04-2020 | TIME_SAVING_ACCOUNT    | 0             |

  Plan du scénario: en tant que manager, je ne peux pas apposer une validation de la direcgtion sur des demandes
    Etant donné que je suis connecté en tant que MANAGER

    Exemples:
      | prenom | nom    | dateDebut  | dateFin    | typeConge              | dureeAttendue |
      | Arthur | Rololo | 01-01-2020 | 01-15-2020 | ANNUAL                 | 0             |
      | Arthur | Rololo | 01-06-2020 | 01-20-2020 | RECOVERY               | 0             |
      | Arthur | Rololo | 01-02-2020 | 02-18-2020 | SPECIAL                | 0             |
      | Arthur | Rololo | 01-02-2020 | 01-23-2020 | WORKING_TIME_REDUCTION | 0             |
      | Arthur | Rololo | 03-04-2020 | 31-04-2020 | TIME_SAVING_ACCOUNT    | 0             |

  Plan du scénario: en tant que manager, je ne peux pas refuser des demandes de personnes hors de mon équipe
    Etant donné que je suis connecté en tant que MANAGER

    Exemples:
      | prenom | nom    | dateDebut  | dateFin    | typeConge              | dureeAttendue |
      | Arthur | Rololo | 01-01-2020 | 01-15-2020 | ANNUAL                 | 0             |
      | Arthur | Rololo | 01-06-2020 | 01-20-2020 | RECOVERY               | 0             |
      | Arthur | Rololo | 01-02-2020 | 02-18-2020 | SPECIAL                | 0             |
      | Arthur | Rololo | 01-02-2020 | 01-23-2020 | WORKING_TIME_REDUCTION | 0             |
      | Arthur | Rololo | 03-04-2020 | 31-04-2020 | TIME_SAVING_ACCOUNT    | 0             |

  Plan du scénario: en tant que manager, je peux refuser des demandes des membres de mon équipe
    Etant donné que je suis connecté en tant que MANAGER

    Exemples:
      | prenom | nom    | dateDebut  | dateFin    | typeConge              | dureeAttendue |
      | Arthur | Rololo | 01-01-2020 | 01-15-2020 | ANNUAL                 | 0             |
      | Arthur | Rololo | 01-06-2020 | 01-20-2020 | RECOVERY               | 0             |
      | Arthur | Rololo | 01-02-2020 | 02-18-2020 | SPECIAL                | 0             |
      | Arthur | Rololo | 01-02-2020 | 01-23-2020 | WORKING_TIME_REDUCTION | 0             |
      | Arthur | Rololo | 03-04-2020 | 31-04-2020 | TIME_SAVING_ACCOUNT    | 0             |


  Plan du scénario: en tant que manager, je ne peux pas remettre en brouillon des demandes hors de mon équipe
    Etant donné que je suis connecté en tant que MANAGER

    Exemples:
      | prenom | nom    | dateDebut  | dateFin    | typeConge              | dureeAttendue |
      | Arthur | Rololo | 01-01-2020 | 01-15-2020 | ANNUAL                 | 0             |
      | Arthur | Rololo | 01-06-2020 | 01-20-2020 | RECOVERY               | 0             |
      | Arthur | Rololo | 01-02-2020 | 02-18-2020 | SPECIAL                | 0             |
      | Arthur | Rololo | 01-02-2020 | 01-23-2020 | WORKING_TIME_REDUCTION | 0             |
      | Arthur | Rololo | 03-04-2020 | 31-04-2020 | TIME_SAVING_ACCOUNT    | 0             |

  Plan du scénario: en tant que manager, je peux pas remettre en brouillon des demandes de mon équipe
    Etant donné que je suis connecté en tant que MANAGER

    Exemples:
      | prenom | nom    | dateDebut  | dateFin    | typeConge              | dureeAttendue |
      | Arthur | Rololo | 01-01-2020 | 01-15-2020 | ANNUAL                 | 0             |
      | Arthur | Rololo | 01-06-2020 | 01-20-2020 | RECOVERY               | 0             |
      | Arthur | Rololo | 01-02-2020 | 02-18-2020 | SPECIAL                | 0             |
      | Arthur | Rololo | 01-02-2020 | 01-23-2020 | WORKING_TIME_REDUCTION | 0             |
      | Arthur | Rololo | 03-04-2020 | 31-04-2020 | TIME_SAVING_ACCOUNT    | 0             |

  Plan du scénario: en tant qu'administrateur, je ne peux pas soumettre des demandes qui ne sont plus en brouillon et qui se chevauchent
    Etant donné que je suis connecté en tant qu'ADMINISTRATEUR
    Quand je crée une demande congés pour "<nom>" "<prenom>" du "<dateDebut1>" à "<dateFin1>" de type "<typeConge1>"
    Et que je soumet la demande congés pour "<nom>" "<prenom>" du "<dateDebut1>" à "<dateFin1>" de type "<typeConge1>" pour validation
    Et que je crée une demande congés pour "<nom>" "<prenom>" du "<dateDebut2>" à "<dateFin2>" de type "<typeConge2>"
    Et que je soumet la demande congés pour "<nom>" "<prenom>" du "<dateDebut2>" à "<dateFin2>" de type "<typeConge2>" pour validation
    Alors une erreur me signalant qu'une demande est déjà en cours pour cette période est produite

    Exemples:
      | prenom | nom    | dateDebut1 | dateFin1   | typeConge1 | dateDebut2 | dateFin2   | typeConge2             |
      | Arthur | Rololo | 05-01-2020 | 10-01-2020 | ANNUAL     | 01-01-2020 | 01-07-2020 | RECOVERY               |
      | Arthur | Rololo | 05-01-2020 | 10-01-2020 | ANNUAL     | 07-01-2020 | 20-07-2020 | SPECIAL                |
      | Arthur | Rololo | 05-01-2020 | 10-01-2020 | ANNUAL     | 01-01-2020 | 20-07-2020 | WORKING_TIME_REDUCTION |
      | Arthur | Rololo | 05-01-2020 | 10-01-2020 | ANNUAL     | 06-01-2020 | 09-07-2020 | TIME_SAVING_ACCOUNT    |
