<template>
  <v-card>
    <v-card-title>
      Bookstore: Books - Employee<v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search Book"
        single-line
        hide-details
      >
      </v-text-field>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="bookstore"
      :search="search"
      @click:row="sellItem"
    >
    </v-data-table>
    <BookstoreDialog
      :opened="dialogVisible"
      :item="selectedItem"
      @refresh="refreshList"
    ></BookstoreDialog>
  </v-card>
</template>

<script>
import api from "../api";
import BookstoreDialog from "../components/BookstoreDialog";

export default {
  name: "Bookstore",
  components: { BookstoreDialog },
  data() {
    return {
      bookstore: [],
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
    async refreshList() {
      console.log("refresh");
      this.dialogVisible = false;
      this.selectedItem = {};
      this.bookstore = await api.bookstore.allBooks();
    },
    sellItem(item) {
      this.selectedItem = item;
      this.dialogVisible = true;
    },
  },
  created() {
    this.refreshList();
  },
  computed: {
    isEmployee() {
      return this.$store.getters["auth/isEmployee"];
    },
  },
};
</script>

<style scoped></style>
