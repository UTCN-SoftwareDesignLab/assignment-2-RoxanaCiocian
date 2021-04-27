import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allBooks() {
    return HTTP.get(BASE_URL + "/bookstore", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  edit(book) {
    return HTTP.patch(BASE_URL + "/bookstore/sell/" + book.id, book, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },

  sell(id, amount) {
    return HTTP.patch(
      BASE_URL + "/bookstore/sell/" + id + "/" + amount,
      {},
      {
        headers: authHeader(),
      }
    ).then((response) => {
      return response.data;
    });
  },
  searchBook(book) {
    return HTTP.get(BASE_URL + "/bookstore/" + book, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
