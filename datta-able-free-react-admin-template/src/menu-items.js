const menuItems = {
  items: [
    {
      id: 'navigation',
      title: 'Navigation',
      type: 'group',
      icon: 'icon-navigation',
      children: [
        {
          id: 'dashboard',
          title: 'Dashboard ADMIN',
          type: 'item',
          icon: 'feather icon-home',
          url: '/app/dashboard/default'
        },
        {
          id: 'etudiant_ing',
          title: 'Etudiant Ingenieur',
          type: 'item',
          icon: 'feather icon-box',
          url: '/ingenieurs'
        },
        {
          id: 'groupe_project',
          title: 'Groupe Projet',
          type: 'item',
          icon: 'feather icon-box',
          url: '/groupeProjects'
        },
        {
          id: 'pfes',
          title: 'PFES',
          type: 'item',
          icon: 'feather icon-box',
          url: '/pfes'
        },
        {
          id: 'pfas',
          title: 'PFAS',
          type: 'item',
          icon: 'feather icon-box',
          url: '/pfas'
        },
        {
          id: 'stage',
          title: 'Stage',
          type: 'item',
          icon: 'feather icon-server',
          url: '/stages'
        },
        {
          id: 'filiere',
          title: 'Filières',
          type: 'item',
          icon: 'feather icon-box',
          url: '/filieres'
        }
      ]
    }
  ]
};

export default menuItems;
