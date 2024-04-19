const makeStateClean = (stateData,stateCell) => {
    if (stateData === 'accepte'){
        stateCell.textContent = 'Accepté';
    }else if(stateData === 'refuse'){
        stateCell.textContent = "Refusé";
    }else if (stateData === 'suspendu'){
        stateCell.textContent = 'Suspendus';
    }else if(stateData === 'initie'){
        stateCell.textContent = 'Initié';
    }else if(stateData === 'annule'){
        stateCell.textContent = 'Annulé';
    }else if(stateData === 'stop follow'){
        stateCell.textContent = 'Suivi stoppé';
    }else if(stateData === 'rencontre'){
        stateCell.textContent = 'Rencontré';
    }
    else stateCell.textContent = stateData;
}

export {makeStateClean}