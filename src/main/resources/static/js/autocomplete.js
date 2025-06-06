$(function () {
    const $input = $('#searchInput');
    const $list = $('#autocompleteList');

    $input.on('keyup', function () {
        const keyword = $input.val();

        if ((/[a-zA-Z]/.test(keyword) && keyword.length < 3) ||
            (/[\u3131-\uD79D]/.test(keyword) && keyword.length < 2)) {
            $list.empty();
            return;
        }

        console.log("자동완성 요청:", keyword);

        $.ajax({
            url: '/autocomplete',
            data: { keyword },
            success: function (data) {
                $list.empty();

                if (data.length === 0) {
                    $list.append('<li class="list-group-item text-muted">검색 결과 없음</li>');
                    return;
                }

                const regex = new RegExp(`(${keyword})`, 'gi');

                data.forEach(item => {
                    const hasPoster = item.posterUrl;
                    const imageTag = hasPoster
                        ? `<img src="https://image.tmdb.org/t/p/w92${item.posterUrl}"
                                alt="poster"
                                style="width: 40px; height: auto; margin-right: 10px;"
                                onerror="this.style.display='none'">`
                        : '';

                    const titleHighlighted = item.title.replace(regex, '<span class="highlight">$1</span>');

                    const li = `
                        <li class="list-group-item autocomplete-item"
                            data-title="${item.title}"
                            data-movieid="${item.movieId}">
                            <div class="d-flex align-items-center">
                                ${imageTag}
                                <span>${titleHighlighted} (${item.releaseYear || ''})</span>
                            </div>
                        </li>`;
                    $list.append(li);
                });
            },
            error: function (xhr, status, error) {
                console.error("자동완성 에러:", error);
            }
        });
    });

    $list.on('click', '.autocomplete-item', function () {
        const movieId = $(this).data('movieid');
        if (movieId) {
            window.location.href = `/movieDetail/${movieId}`;
        }
    });

    $(document).on('click', function (e) {
        if (!$(e.target).closest('#searchForm').length) {
            $list.empty();
        }
    });
});
