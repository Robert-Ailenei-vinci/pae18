
import {getAuthenticatedUser} from "../../../utils/auths";

const user = getAuthenticatedUser();

async function fetchStudentsWithStages(filtre){
    console.log("fetchStudentsWithStages:",filtre);
    const options = {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `${user.token}`,
        },
      };
      try {
        const responseStudsWithStage = await fetch(
            `http://localhost:3000/users/studWithStage/${encodeURIComponent(filtre)}`, options);
        const studsWithStageData = await responseStudsWithStage.json();
        console.log("fetchStudentsWithStages:",studsWithStageData);


        return studsWithStageData;
      } catch (error) {
        console.log(error);
      }
}

async function fetchStudentsWithNoStages(filtre){
    console.log("fetchStudentsWithNoStages:",filtre);
    const options = {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `${user.token}`,
        },
      };
      try {
        const responseStudsWithStage = await fetch(
            `http://localhost:3000/users/studWithNoStage/${encodeURIComponent(filtre)}`, options);
        
        
    
        const studsWithNoStageData = await responseStudsWithStage.json();
        console.log("fetchStudentsWithNoStagesfinished:",studsWithNoStageData);

        return studsWithNoStageData;
      } catch (error) {
        console.log(error);
      }
}

export{
    fetchStudentsWithStages,
    fetchStudentsWithNoStages
}