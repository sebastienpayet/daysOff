#language: fr

Fonctionnalité: : créer une équipe

  Contexte:
    Etant donné que des equipes existent
      | nom          |
      | MANAGEMENT   |
      | LOCAL_POLICE |

    Etant donné que des utilisateurs existent
      | prenom | nom       | role          | services     | avecOuSansEnfantMineur | email              |
      | Elise  | Moualala  | ADMINISTRATOR | MANAGEMENT   | avec                   | elise@test.com     |
      | Arthur | Rololo    | USER          | LOCAL_POLICE | sans                   | arthur@test.com    |
      | Jose   | DeLaPampa | TEAM_MANAGER  | LOCAL_POLICE | sans                   | delapampa@test.com |

  Plan du scénario: en tant qu'administrateur, je peux créer une nouvelle équipe
    Etant donné que je suis connecté en tant qu'ADMINISTRATEUR
    Quand je crée une équipe "<nom>"
    Alors l'équipe "<nom>" existe dans l'application
    Et aucune erreur n'a été produite
    Exemples:
      | nom              |
      | équipe 1         |
      | 123987ç!èé&!è§'  |
      | Mon équipe a moi |
      | Max team         |
      | 'José team'      |

  Plan du scénario: en tant que manager, je ne peux pas créer une nouvelle équipe
    Etant donné que je suis connecté en tant que MANAGER
    Quand je crée une équipe "<nom>"
    Alors Une erreur signalant que je n'ai pas les droits est produite
    Exemples:
      | nom      |
      | équipe 1 |

  Plan du scénario: en tant qu'utilisateur, je ne peux pas créer une nouvelle équipe
    Etant donné que je suis connecté en tant qu'USAGER
    Quand je crée une équipe "<nom>"
    Alors Une erreur signalant que je n'ai pas les droits est produite
    Exemples:
      | nom      |
      | équipe 1 |

  Plan du scénario: en tant qu'administrateur, je ne peux pas créer une nouvelle équipe dont le nom
  est déjà utilisé
    Etant donné que je suis connecté en tant qu'ADMINISTRATEUR
    Quand je crée une équipe "<nom>"
    Alors Une erreur signalant que nom d'équipe est déjà utilisé est produite
    Exemples:
      | nom          |
      | LOCAL_POLICE |

  Plan du scénario: en tant qu'administrateur, je peux réutiliser le nom d'une équipe qui a été supprimée
    Etant donné que je suis connecté en tant qu'ADMINISTRATEUR
    Quand je crée une équipe "<nom>"
    Et que je supprime l'équipe "<nom>"
    Quand je crée une équipe "<nom>"
    Alors l'équipe "<nom>" existe dans l'application
    Et il n'existe qu'une équipe portant le nom "<nom>"
    Exemples:
      | nom      |
      | LOCAL_POLICE |
