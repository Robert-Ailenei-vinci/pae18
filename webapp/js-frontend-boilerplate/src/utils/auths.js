const STORE_NAME = 'user';
const REMEMBER_ME = 'remembered';

let currentUser;

const apiUrl = "http://localhost:3000/users?pattern=";

const getAuthenticatedUser = () => {
  if (currentUser !== undefined) return currentUser;

  const remembered = getRememberMe();
  const serializedUser = remembered
    ? localStorage.getItem(STORE_NAME)
    : sessionStorage.getItem(STORE_NAME);

  if (!serializedUser) return undefined;

  currentUser = JSON.parse(serializedUser);
  return currentUser;
};

const setAuthenticatedUser = (authenticatedUser) => {
  const serializedUser = JSON.stringify(authenticatedUser);
  const remembered = getRememberMe();
  if (remembered) localStorage.setItem(STORE_NAME, serializedUser);
  else sessionStorage.setItem(STORE_NAME, serializedUser);

  currentUser = authenticatedUser;
};

const isAuthenticated = () => currentUser !== undefined;

const clearAuthenticatedUser = () => {
  localStorage.clear();
  sessionStorage.clear();
  currentUser = undefined;
};

function getRememberMe() {
  const rememberedSerialized = localStorage.getItem(REMEMBER_ME);
  const remembered = JSON.parse(rememberedSerialized);
  return remembered;
}

function setRememberMe(remembered) {
  const rememberedSerialized = JSON.stringify(remembered);
  localStorage.setItem(REMEMBER_ME, rememberedSerialized);
}


async function getUsersWithNames(pattern) {
  try {
    
    let url = apiUrl;

    if (pattern) {
      url += `?pattern=${encodeURIComponent(pattern)}`;
    }
      const response = await fetch(url);
      if (!response.ok) {
          throw new Error('Error fetching users');
      }

      const users = await response.json();
      return users;
  } catch (error) {
      console.error('Error:', error);
      throw error;
  }
}



export {
  getAuthenticatedUser,
  setAuthenticatedUser,
  isAuthenticated,
  clearAuthenticatedUser,
  getRememberMe,
  setRememberMe,
  getUsersWithNames,
};
