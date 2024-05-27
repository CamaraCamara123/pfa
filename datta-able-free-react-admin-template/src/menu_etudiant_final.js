const menuItems_etudaint_final = {
  items: [
    {
      id: 'navigation',
      title: 'Navigation',
      type: 'group',
      icon: 'icon-navigation',
      children: [
        {
          id: 'dashboard Etudiant',
          title: 'Dashboard',
          type: 'item',
          icon: 'feather icon-home',
          url: '/app/dashboard/default'
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
          id: 'stage',
          title: 'Stage',
          type: 'item',
          icon: 'feather icon-box',
          url: '/stages'
        }
      ]
    }
  ]
};

export default menuItems_etudaint_final;
