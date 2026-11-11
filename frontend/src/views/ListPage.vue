<script setup>
import { ref, watch } from 'vue'
import { api, errText } from '../api'

const props = defineProps({
  url: String,
  title: String,
  filters: Boolean,
  empty: { type: String, default: 'Nothing here yet.' },
})

const q = ref({ keyword: '', city: '' })
const page = ref(1)
const data = ref({ items: [], total: 0, size: 10 })
const err = ref('')

async function load() {
  err.value = ''
  try {
    const params = { page: page.value, size: 10 }
    if (props.filters) {
      if (q.value.keyword) params.keyword = q.value.keyword
      if (q.value.city) params.city = q.value.city
    }
    data.value = (await api.get(props.url, { params })).data
  } catch (e) {
    err.value = errText(e)
  }
}
function search() { page.value = 1; load() }
function go(n) { page.value = n; load() }
const pages = () => Math.max(1, Math.ceil(data.value.total / (data.value.size || 10)))

watch(() => props.url, () => { page.value = 1; load() }, { immediate: true })
</script>

<template>
  <h1>{{ title }}</h1>
  <div v-if="filters" class="row">
    <input v-model="q.keyword" placeholder="Keyword" @keyup.enter="search" />
    <input v-model="q.city" placeholder="City" @keyup.enter="search" />
    <button @click="search">Search</button>
  </div>
  <p v-if="err" class="err">{{ err }}</p>
  <p v-else-if="!data.items.length" class="muted">{{ empty }}</p>
  <RouterLink v-for="p in data.items" :key="p.id" :to="'/posts/' + p.id" class="item">
    <div class="item-head">
      <span class="tag">{{ p.category }}</span>
      <strong>{{ p.title }}</strong>
    </div>
    <div class="muted">
      {{ p.city || 'No city' }}<span v-if="p.price != null"> – {{ p.price }}</span>
      – by {{ p.authorName }}
    </div>
  </RouterLink>
  <div v-if="data.total > data.size" class="row pager">
    <button class="ghost" :disabled="page <= 1" @click="go(page - 1)">Previous</button>
    <span class="muted">Page {{ page }} of {{ pages() }}</span>
    <button class="ghost" :disabled="page >= pages()" @click="go(page + 1)">Next</button>
  </div>
</template>
