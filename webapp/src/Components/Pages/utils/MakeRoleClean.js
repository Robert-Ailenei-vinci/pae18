const roleMapping = {
    'etudiant': 'étudiant',
};

const makeRoleClean = (roleData, roleCell) => {
    const cleanText = roleMapping[roleData] || roleData; // Obtenir le texte du mappage ou utiliser l'original
    roleCell.textContent = cleanText; // Assigner le texte à la cellule
};

export { makeRoleClean };
