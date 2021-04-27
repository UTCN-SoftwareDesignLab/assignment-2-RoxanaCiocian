import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allBooks() {
    return HTTP.get(BASE_URL + "/bookstore/books", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },

  allBooks1() {
    return HTTP.get(BASE_URL + "/bookstore/", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },

  create(book) {
    return HTTP.post(BASE_URL + "/bookstore/books", book, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  edit(id, book) {
    return HTTP.put(BASE_URL + "/bookstore/books/" + id, book, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  deleteBook(id) {
    return HTTP.delete(BASE_URL + "/bookstore/books/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  deleteAll() {
    return HTTP.delete(BASE_URL + "/bookstore/books", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  PDF() {
    console.log("PDF");
    return HTTP.get(BASE_URL + "/bookstore/books/export/PDF", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  CSV() {
    console.log("CSV");
    return HTTP.get(BASE_URL + "/bookstore/books/export/CSV", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
