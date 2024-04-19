const usePathParams = () => {
  const urlPath = window.location.pathname;
  const pathSegments = urlPath.split('/');
  return pathSegments[pathSegments.length - 1];
}

const useQueryParams = () => {
  const params = new URLSearchParams(window.location.search);
  return Object.fromEntries(params);
}

export { useQueryParams, usePathParams };