import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allBooks() {
    return HTTP.get(BASE_URL + "/bookstore", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },

  sell(id, amount) {
    return HTTP.patch(BASE_URL + "/bookstore/" + id + "/" + amount, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
