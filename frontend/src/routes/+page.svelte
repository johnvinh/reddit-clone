<script>
    import {onMount} from "svelte";
    let users = [];

    onMount(async () => {
        const res = await fetch("/api/user");
        users = await res.json();
    });
</script>

<ul>
    {#each users as user}
        <li>{user["username"]}</li>
    {/each}
</ul>
