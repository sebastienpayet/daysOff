#language: fr

Fonctionnalité: : supprimer un usager

  Contexte:
    Etant donné que des equipes existent
      | nom          |
      | ROADWORK     |
      | LOCAL_POLICE |
      | SECRETARIAT  |
      | TECHNICAL    |
      | SCHOOL       |
      | PADDING_POOL |
      | MANAGEMENT   |

    Etant donné que des utilisateurs existent
      | prenom | nom      | role          | services   | avecOuSansEnfantMineur | email          |
      | Elise  | Malala | ADMINISTRATOR | MANAGEMENT | avec                   | elise@test.com |
      | Arthur | Rololo | USER            | LOCAL_POLICE | sans                   | arthur@test.com    |
      | Jose   | DeLaPampa      | TEAM_MANAGER | LOCAL_POLICE | sans                   | delapampa@test.com |

  Plan du scénario: en tant qu'administrateur, je peux supprimer un usager
    Etant donné que je suis connecté en tant qu'ADMINISTRATEUR
    Et que je crée un USAGER "<nom>" "<prenom>" dans le service "<services>", "<avecOuSansEnfantMineur>" enfant mineur "<email>"
    Et que l'usager "<nom>" "<prenom>" dans le service "<services>", "<avecOuSansEnfantMineur>"  enfant mineur "<email>", existe dans l'application
    Quand je supprime un USAGER "<nom>" "<prenom>"
    Alors l'usager "<nom>" "<prenom>" n'existe plus dans l'application
    Et aucune erreur n'a été produite
    Exemples:
      | prenom    | nom             | services                          | avecOuSansEnfantMineur | email         |
      | Robert    | Dupont          | ROADWORK                          | sans                   | add1@test.com |
      | Sébastien | Gourmellin      | LOCAL_POLICE                      | avec                   | add1@test.com |
      | Fathia    | Jesaispassonnom | SECRETARIAT                       | sans                   | add1@test.com |
      | Max       | Lamenace        | TECHNICAL                         | avec                   | add1@test.com |
      | José      | De la pampa     | TECHNICAL, ROADWORK               | avec                   | add1@test.com |
      | Louise    | Labrocante      | SCHOOL, PADDING_POOL, SECRETARIAT | sans                   | add1@test.com |

  Plan du scénario: en tant qu'usager, je ne peux pas supprimer un usager
    Etant donné que je suis connecté en tant qu'USAGER
    Quand je supprime un USAGER "<nom>" "<prenom>"
    Alors Une erreur signalant que je n'ai pas les droits est produite
    Exemples:
      | prenom | nom      |
      | Elise  | Malala |

  Plan du scénario: en tant que manager, je ne peux pas supprimer un usager
    Etant donné que je suis connecté en tant que MANAGER
    Quand je supprime un USAGER "<nom>" "<prenom>"
    Alors Une erreur signalant que je n'ai pas les droits est produite
    Exemples:
      | prenom | nom      |
      | Elise  | Malala |
