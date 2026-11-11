<script setup>
import { ref, onMounted } from 'vue'
import { api, errText } from '../api'

const all = ref([])
const chosen = ref([])
const err = ref('')
const info = ref('')

onMounted(async () => {
  try {
    all.value = (await api.get('/tags')).data
    chosen.value = (await api.get('/users/me/tags')).data.map((t) => t.id)
  } catch (e) {
    err.value = errText(e)
  }
})

function toggle(id) {
  const i = chosen.value.indexOf(id)
  if (i >= 0) chosen.value.splice(i, 1)
  else chosen.value.push(id)
}

async function save() {
  err.value = ''
  info.value = ''
  try {
    await api.put('/users/me/tags', { tagIds: chosen.value })
    info.value = 'Interests saved.'
  } catch (e) {
    err.value = errText(e)
  }
}
</script>

<template>
  <h1>My interests</h1>
  <p class="muted">Pick up to 10 tags. Recommendations use the tags you share with a post.</p>
  <p v-if="err" class="err">{{ err }}</p>
  <p v-if="info" class="ok">{{ info }}</p>
  <div class="chips">
    <button v-for="t in all" :key="t.id" :class="['chip', { on: chosen.includes(t.id) }]" @click="toggle(t.id)">
      {{ t.name }}
    </button>
  </div>
  <button :disabled="chosen.length > 10" @click="save">Save interests</button>
</template>
