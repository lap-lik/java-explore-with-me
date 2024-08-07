package ru.practicum.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.compilation.dao.CompilationDAO;
import ru.practicum.compilation.dto.CompilationDtoIn;
import ru.practicum.compilation.dto.CompilationDtoOut;
import ru.practicum.compilation.dto.CompilationDtoUpdate;
import ru.practicum.compilation.mapper.CompilationMapper;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.exception.NotFoundException;
import ru.practicum.util.EwmPageRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompilationServiceImpl implements CompilationService {

    private final CompilationDAO compilationDAO;
    private final CompilationMapper compilationMapper;

    @Override
    @Transactional
    public CompilationDtoOut createByAdmin(CompilationDtoIn inputDTO) {

        Compilation compilation = compilationMapper.inputDtoToEntity(inputDTO);
        Compilation savedCompilation = compilationDAO.save(compilation);

        return compilationMapper.entityToOutputDto(savedCompilation);
    }

    @Override
    @Transactional
    public void deleteByAdmin(long compId) {

        checkExistsCompilationById(compId);
        compilationDAO.deleteById(compId);
    }

    @Override
    @Transactional
    public CompilationDtoOut updateByAdmin(long compId, CompilationDtoUpdate updateDto) {

        Compilation compilation = getCompilation(compId);
        Compilation updatedCompilation = compilationMapper.update(updateDto, compilation);

        return compilationMapper.entityToOutputDto(updatedCompilation);
    }

    @Override
    public List<CompilationDtoOut> getAllByPublic(boolean pinned, int from, int size) {

        Pageable pageable = EwmPageRequest.of(from, size);
        List<Compilation> compilations = compilationDAO.findAllByPinned(pinned, pageable).getContent();

        return compilationMapper.entitiesToOutputDtos(compilations);
    }

    @Override
    public CompilationDtoOut getByPublic(Long compId) {

        return compilationMapper.entityToOutputDto(getCompilation(compId));
    }

    private Compilation getCompilation(Long compId) {

        return compilationDAO.findById(compId)
                .orElseThrow(() -> NotFoundException.builder()
                        .message(String.format("The compilation with the ID=`%d` was not found.", compId))
                        .build());
    }

    private void checkExistsCompilationById(long compId) {

        boolean isExist = compilationDAO.existsById(compId);
        if (!isExist) {
            throw NotFoundException.builder()
                    .message(String.format("The compilation with the ID=`%d` was not found.", compId))
                    .build();
        }
    }
}
