<template>
  <v-card>
    <v-card-title>
      Books - Admin
      <v-spacer></v-spacer>

      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addBook">Add Book</v-btn>
      <v-btn @click="exportPDF">PDF Rep</v-btn>
      <v-btn @click="exportCSV">CSV Rep</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="items"
      :search="search"
      @click:row="editBook"
    ></v-data-table>
    <BookDialog
      :opened="dialogVisible"
      :item="selectedItem"
      @refresh="refreshList"
    ></BookDialog>
    <v-btn @click="goToUsers">Go to Users</v-btn>
  </v-card>
</template>

<script>
import api from "../api";
import BookDialog from "../components/BookDialog";
import router from "../router";

export default {
  name: "BookList",
  components: { BookDialog },
  data() {
    return {
      items: [],
      search: "",
      headers: [
        {
          text: "Title",
          align: "start",
          sortable: false,
          value: "title",
        },
        { text: "Author", value: "author" },
        { text: "Genre", value: "genre" },
        { text: "Price", value: "price" },
        { text: "Quantity", value: "quantity" },
      ],
      dialogVisible: false,
      selectedItem: {},
    };
  },
  methods: {
    editBook(item) {
      this.selectedItem = item;
      this.dialogVisible = true;
    },
    addBook() {
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedItem = {};
      this.items = await api.items.allBooks();
    },
    exportPDF() {
      api.items.PDF();
    },
    exportCSV() {
      api.items.CSV();
    },
    goToUsers() {
      router.push("/users");
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
