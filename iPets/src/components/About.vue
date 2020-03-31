<template>
    <div class="container mt-3">
        <ul class="nav nav-tabs nav-fill" style="margin-top: 20px; color: #e3f2fd;">
            <li class="nav-item">
                <a class="nav-link" data-toggle="tab" href="#motivation">專案動機</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" data-toggle="tab" href="#public">給大眾</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" data-toggle="tab" href="#special">特別</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" data-toggle="tab" href="#member">組員介紹</a>
            </li>
        </ul>
        <br>
        <div id="member" class="container tab-pane fade">
            <div class="card" style="max-width: 300px; max-height: 500px;">

                <div class="card-body p-0">
                    <img class="rounded" src="../assets/04.jpg" style="max-width: 100%; height: auto;  box-sizing: border-box;">
                </div>
                <div class="card-footer">
                    QQ
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
  name: 'about'
}
</script>
