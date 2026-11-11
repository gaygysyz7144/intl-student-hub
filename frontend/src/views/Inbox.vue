<script setup>
import { ref, onMounted } from 'vue'
import { api, errText } from '../api'

const data = ref({ items: [], total: 0 })
const err = ref('')

onMounted(async () => {
  try {
    data.value = (await api.get('/messages', { params: { page: 1, size: 20 } })).data
  } catch (e) {
    err.value = errText(e)
  }
})
</script>

<template>
  <h1>Inbox</h1>
  <p v-if="err" class="err">{{ err }}</p>
  <p v-else-if="!data.items.length" class="muted">No messages yet.</p>
  <div v-for="m in data.items" :key="m.id" class="item">
    <strong>{{ m.senderName }}</strong>
    <span class="muted"> – {{ (m.createdAt || '').replace('T', ' ').slice(0, 16) }}</span>
    <div class="body">{{ m.content }}</div>
  </div>
</template>
