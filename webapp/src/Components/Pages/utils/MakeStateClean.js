const stateMapping = {
    'accepte': 'Accepté',
    'refuse': 'Refusé',
    'suspendu': 'Suspendu',
    'initie': 'Initié',
    'annule': 'Annulé',
    'stop follow': 'Suivi stoppé',
    'rencontre': 'Rencontré',
};

const makeStateClean = (stateData, stateCell) => {
    const cleanText = stateMapping[stateData] || stateData; // Obtenir le texte du mappage ou utiliser l'original
    stateCell.textContent = cleanText; // Assigner le texte à la cellule
};

export { makeStateClean };
