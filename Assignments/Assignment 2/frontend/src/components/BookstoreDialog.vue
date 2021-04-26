<template>
  <v-card>
    <v-toolbar color="primary" dark>
      {{ "Sell book" }}
    </v-toolbar>
    <v-form>
      <v-text-field v-model="item.title" label="Title" />
      <v-text-field v-model="item.author" label="Author" />
      <v-text-field v-model="item.genre" label="Genre" />
      <v-text-field v-model="item.price" label="Price" />
      <v-text-field v-model="item.quantity" label="Quantity" />
      <v-text-field v-model="quantity" label="Amount to sell" />
    </v-form>
    <v-card-actions>
      <v-btn @click="persist">
        {{ "Sell" }}
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import api from "../api";

export default {
  name: "BookStoreDialog",
  props: {
    item: Object,
    quantity: Number,
    opened: Boolean,
  },
  methods: {
    persist() {
      api.bookstore
        .sell(this.item.id, this.quantity)
        .then(() => this.$emit("refresh"));
    },
  },
};
</script>

<style scoped></style>
