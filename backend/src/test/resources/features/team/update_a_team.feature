#language: fr

Fonctionnalité: : modifier une équipe

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

  Plan du scénario: en tant qu'administrateur, je peux modifier le nom d'une équipe
    Etant donné que je suis connecté en tant qu'ADMINISTRATEUR
    Quand je crée une équipe "<nom>"
    Et que je modifie de l'équipe "<nom>" par "<nouveau_nom>"
    Alors l'équipe "<nouveau_nom>" existe dans l'application
    Et l'équipe "<nom>" n'existe pas dans l'application
    Et aucune erreur n'a été produite
    Exemples:
      | nom              | nouveau_nom |
      | équipe 1         | blabla 2    |
      | 123987ç!èé&!è§'  | blibli 3    |
      | Mon équipe a moi | bloblo      |
      | Max team         | blublu      |
      | 'José team'      | nouvtest    |

  Plan du scénario: en tant que manager, je ne peux pas modifier une équipe
    Etant donné que je suis connecté en tant que MANAGER
    Quand je modifie de l'équipe "<nom>" par "<nouveau_nom>"
    Alors Une erreur signalant que je n'ai pas les droits est produite
    Exemples:
      | nom          | nouveau_nom |
      | LOCAL_POLICE | test        |

  Plan du scénario: en tant qu'utilisateur, je ne peux pas modifier une équipe
    Etant donné que je suis connecté en tant qu'USAGER
    Quand je modifie de l'équipe "<nom>" par "<nouveau_nom>"
    Alors Une erreur signalant que je n'ai pas les droits est produite
    Exemples:
      | nom          | nouveau_nom |
      | LOCAL_POLICE | test        |

  Plan du scénario: en tant qu'administrateur, je ne peux pas modifier une équipe avec un nom déjà utilisé
  est déjà utilisé
    Etant donné que je suis connecté en tant qu'ADMINISTRATEUR
    Quand je modifie de l'équipe "<nom>" par "<nouveau_nom>"
    Alors Une erreur signalant que nom d'équipe est déjà utilisé est produite
    Exemples:
      | nom          | nouveau_nom |
      | LOCAL_POLICE | MANAGEMENT  |

  Plan du scénario: en tant qu'administrateur, je peux réutiliser le nom d'une équipe qui a été supprimée
    Etant donné que je suis connecté en tant qu'ADMINISTRATEUR
    Quand je crée une équipe "<nom>"
    Et que je supprime l'équipe "<nom>"
    Quand je crée une équipe "<nom>"
    Alors il n'existe qu'une équipe portant le nom "<nom>"
    Exemples:
      | nom  |
      | test |
